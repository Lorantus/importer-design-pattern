package com.laurent.importer.error;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.model.QuestionReponseImport;
import com.laurent.importer.model.ReponseValidee;
import lombok.AllArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class ImportLigneErrorMessage implements ErrorMessage {
    private final int ligne;
    private final String question;
    private final String message;

    public static ImportLigneErrorMessage createMessage(QuestionReponseImport ligne, String message) {
        return new ImportLigneErrorMessage(ligne.getLigne(), ligne.getQuestion(), message);
    }

    public static ImportLigneErrorMessage createMessage(ReponseValidee reponseValidee, String message) {
        return new ImportLigneErrorMessage(reponseValidee.getLigne(), reponseValidee.getQuestion().getQuestion(), message);
    }

    public static ImportLigneErrorMessage createMessage(int ligne, String question, String message) {
        return new ImportLigneErrorMessage(ligne, question, message);
    }

    @Override
    public String getErrorMessage() {
        return String.format("%d, \"%s\", %s", ligne, question, message);
    }

    @Override
    public String toString() {
        return getErrorMessage();
    }
}
