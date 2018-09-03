package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.command.ReponseValideeLigne;
import com.laurent.importer.domain.Question;
import com.laurent.importer.domain.Questionnaire;
import com.laurent.importer.domain.Reponse;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.ReponseValidee;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static com.laurent.importer.ImportReponseCollector.doublonReponseValidatorCollector;
import static com.laurent.importer.error.ImportLigneErrorMessage.createMessage;

public class ImporterReponsesService extends ImportError {

    private boolean isSame(String existant, String validee) {
        return (existant == null && validee == null) || (existant != null && existant.equals(validee));
    }

    private Predicate<ReponseValidee> isSameReponse(ReponseValidee reponseExistante) {
        return reponseValidee -> (reponseExistante.isNonConcernee() == reponseValidee.isNonConcernee())
                && isSame(reponseExistante.getReponse(), reponseValidee.getReponse());
    }

    private Collector<Optional<ReponseValidee>, Collection<ReponseValidee>, Collection<ReponseValidee>> getDoublonReponseValidatorCollector() {
        return doublonReponseValidatorCollector((reponseValideeExistante, reponseValidee) ->
                Validator.<ReponseValidee, ErrorMessage>of(reponseValidee)
                        .onError(this::add)
                        .validate(isSameReponse(reponseValideeExistante),
                                () -> createMessage(
                                        reponseValidee,
                                        String.format("Cette question est déjà répondue avec une valeur différente ligne %d",
                                                reponseValideeExistante.getLigne())))
        );
    }

    private Function<QuestionReponseImport, Optional<ReponseValidee>> createReponseValidee(Collection<Question> questions) {
        LibelleQuestionFinder libelleQuestionFinder = new LibelleQuestionFinder(questions);
        ReponseValideeLigne reponseValideeLigne = new ReponseValideeLigne(this::add);
        return ligne -> {
            Optional<Question> finderQuestion = libelleQuestionFinder.getQuestion(ligne.getQuestion());
            if (finderQuestion.isPresent()) {
                return reponseValideeLigne.execute(ligne, finderQuestion.get());
            }
            add(createMessage(ligne, "Cette question n'existe pas."));
            return Optional.empty();
        };
    }

    public void execute(DataSource datas, Questionnaire questionnaire) {
        Collection<ReponseValidee> reponseValidees = LecteurImportReponses.createLecteur(datas)
                .parse()
                .map(createReponseValidee(questionnaire.getQuestions().values()))
                .collect(getDoublonReponseValidatorCollector());

        Collection<Reponse> reponsesAImporter = new ReponseToApply(questionnaire)
                .apply(reponseValidees);

        if (!hasError()) {
            new ApplicatibiliteValidator(questionnaire)
                    .onError(this::add)
                    .validate(reponsesAImporter);
        }

        if (hasError()) {
            throw new IllegalArgumentException(getErrorMessage());
        }
    }
}
