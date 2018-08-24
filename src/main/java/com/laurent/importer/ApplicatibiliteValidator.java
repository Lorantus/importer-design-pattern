package com.laurent.importer;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.domain.Questionnaire;
import com.laurent.importer.domain.Reponse;
import com.laurent.importer.error.ImportFichierErrorMessage;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ApplicatibiliteValidator extends ImportError {
    private final Questionnaire questionnaire;
    private Consumer<ErrorMessage> errorConsumer;

    public ApplicatibiliteValidator(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public ApplicatibiliteValidator onError(Consumer<ErrorMessage> consumer) {
        this.errorConsumer = consumer;
        return this;
    }

    private boolean checkApplicabilite(Collection<Reponse> reponseValidee) {
        return !questionnaire.getQuestions().isEmpty() && reponseValidee.size() == questionnaire.getQuestions().size();
    }

    private Predicate<Question> isReponseExiste(Collection<Reponse> reponseValidee) {
        return question -> reponseValidee.stream()
                .flatMap(reponse -> questionnaire.getQuestions().values().stream())
                .anyMatch(question::equals);
    }

    public void validate(Collection<Reponse> reponseValidee) {
        questionnaire.getQuestions().values()
                .forEach(question ->
                        Validator.<Question, ErrorMessage>of(question)
                                .onError(errorConsumer)
                                .validate(isReponseExiste(reponseValidee),
                                        () -> new ImportFichierErrorMessage(
                                                String.format("La question \"%s\" n'existe pas", question.getQuestion()))));
        Validator.<Collection<Reponse>, ErrorMessage>of(reponseValidee)
                .onError(errorConsumer)
                .validate(this::checkApplicabilite,
                        () -> new ImportFichierErrorMessage("Le nombre de réponses n'est pas égal au nombre de questions"));
    }
}
