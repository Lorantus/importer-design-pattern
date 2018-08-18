package com.laurent.importer.domain;

import com.laurent.importer.model.TypeQuestion;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class QuestionReponse {
    private UUID id;
    private String question;
    private TypeQuestion typeQuestion;
    private String reponse;
    private String commentaire;
    private boolean nonConcerne;
    private List<Choix> choix;
}
