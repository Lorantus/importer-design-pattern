package com.laurent.importer.command;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.comparator.IgnoreCaseComparator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.ReponseValidee;

import java.util.Optional;
import java.util.function.Consumer;

import static com.laurent.importer.model.ReponseValidee.createReponseValidee;
import static com.laurent.importer.model.ReponseValidee.createReponseValideeNonConcerne;

public class ReponseValideeLigne extends ImportError {
    private static final IgnoreCaseComparator NON_CONCERNE = new IgnoreCaseComparator("Non concerné");

    private final TypeQuestionValidatorLigne typeQuestionValidatorCommand;

    public ReponseValideeLigne(Consumer<ErrorMessage> errorConsumer) {
        typeQuestionValidatorCommand = new TypeQuestionValidatorLigne()
                .onError(errorConsumer);
    }

    private boolean isNonConcerneReponse(QuestionReponseImport ligneImport, Question question) {
        return question.getTypeQuestion().isNonConcernee() && NON_CONCERNE.is(ligneImport.getReponse());
    }

    public Optional<ReponseValidee> execute(QuestionReponseImport ligne, Question question) {
        return isNonConcerneReponse(ligne, question) ?
                Optional.of(createReponseValideeNonConcerne(ligne.getLigne(), question)) :
                typeQuestionValidatorCommand
                        .validator(ligne, question)
                        .map(reponseValue -> {
                            ReponseValidee reponseValidee = createReponseValidee(ligne.getLigne(), question);
                            reponseValidee.setReponse(reponseValue);
                            reponseValidee.setCommentaire(ligne.getCommentaire());
                            return reponseValidee;
                        });
    }
}
