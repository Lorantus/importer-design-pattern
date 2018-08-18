package com.laurent.importer.compoments;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionComparator {
    private static QuestionComparator THE_INSTANCE;

    private final String libelleQuestion;

    public static QuestionComparator getInstance(String libelleQuestion) {
        if(THE_INSTANCE == null) {
            THE_INSTANCE = new QuestionComparator(libelleQuestion.trim());
        }
        return THE_INSTANCE;
    }

    public boolean has(String libelle) {
        return libelleQuestion.equalsIgnoreCase(libelle);
    }
}
