package com.laurent.importer.model;

import lombok.Getter;

@Getter
public enum TypeQuestion {
    BINAIRE(false), NUMERIC(true), QCU(false), QCM(true), TEXTE(true);

    private final boolean nonConcernee;

    TypeQuestion(boolean nonConcernee) {
        this.nonConcernee = nonConcernee;
    }
}
