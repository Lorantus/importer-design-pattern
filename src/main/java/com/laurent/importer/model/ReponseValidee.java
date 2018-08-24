package com.laurent.importer.model;

import com.laurent.importer.domain.Question;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ReponseValidee {
    private final int ligne;
    private final Question question;
    private String reponse;
    private String commentaire;
    private boolean nonConcernee;

    public static ReponseValidee createReponseValideeNonConcerne(int ligne, Question question) {
        ReponseValidee reponseValidee = new ReponseValidee(ligne, question);
        reponseValidee.setNonConcernee(true);
        return reponseValidee;
    }

    public static ReponseValidee createReponseValidee(int ligne, Question question) {
        ReponseValidee reponseValidee = new ReponseValidee(ligne, question);
        reponseValidee.setNonConcernee(false);
        return reponseValidee;
    }

    public boolean isSameQuestion(Question question) {
        if(this.question == question) return true;
        return this.question != null && this.question.getId().equals(question.getId());
    }

    private void setNonConcernee(boolean nonConcernee) {
        this.nonConcernee = nonConcernee;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
