package com.laurent.importer.api.error;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Getter
public class ImportError implements ErrorMessage {
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    public boolean hasError() {
        return !errorMessages.isEmpty();
    }

    protected void add(ErrorMessage error) {
        errorMessages.add(error);
    }

    @Override
    public String getErrorMessage() {
        return errorMessages.stream()
                .map(ErrorMessage::getErrorMessage)
                .collect(joining("\n"));
    }

    @Override
    public String toString() {
        return getErrorMessage();
    }
}
