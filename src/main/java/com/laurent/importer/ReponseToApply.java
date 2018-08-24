package com.laurent.importer;

import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.domain.Questionnaire;
import com.laurent.importer.domain.Reponse;
import com.laurent.importer.model.ReponseValidee;

import java.util.Collection;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

public class ReponseToApply extends ImportError {

    private final Questionnaire questionnaire;

    public ReponseToApply(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    private boolean isNotSameReponse(Reponse reponse, ReponseValidee reponseValidee) {
        return true;
    }

    private Reponse merge(ReponseValidee reponseValidee, Reponse reponse) {
        if (reponse.isNonConcerne() != reponseValidee.isNonConcernee()) {
            reponse.setNonConcerne(reponseValidee.isNonConcernee());
        }
        if (!reponseValidee.isNonConcernee() && isNotSameReponse(reponse, reponseValidee)) {
            reponse.setReponse(reponseValidee.getReponse());
            reponse.setCommentaire(reponseValidee.getCommentaire());
        }
        return reponse;
    }

    public Collection<Reponse> apply(Collection<ReponseValidee> reponseValidees) {
        return reponseValidees.stream()
                .map(reponseValidee -> merge(reponseValidee,
                        questionnaire.getReponse(reponseValidee.getQuestion())
                                .orElse(new Reponse(UUID.randomUUID(), reponseValidee.getQuestion().getId()))))
                .peek(reponse -> questionnaire.getReponses().putIfAbsent(reponse.getQuestion(), reponse))
                .collect(toSet());
    }
}
