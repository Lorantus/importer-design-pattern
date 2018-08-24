package com.laurent.importer.error;

import com.laurent.importer.api.error.ErrorMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImportFichierErrorMessage implements ErrorMessage {
    private final String message;

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getErrorMessage();
    }
}
