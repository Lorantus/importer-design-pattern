package com.laurent.importer.comparator;

public class QuestionLibelleComparator extends IgnoreCaseComparator {
    public QuestionLibelleComparator(String libelleQuestion) {
        super(libelleQuestion.trim());
    }
}
