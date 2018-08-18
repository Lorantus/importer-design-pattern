package com.laurent.importer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionReponseImport {
    private final int ligne;
    private final String question;
    private final String reponse;
    private final String commentaire;
}
