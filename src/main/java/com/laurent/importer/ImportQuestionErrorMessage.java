package com.laurent.importer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImportQuestionErrorMessage {
    private final int ligne;
    private final String question;
    private final String message;

    @Override
    public String toString() {
        return String.format("%d, \"%s\", %s", ligne, question, message);
    }
}
