package com.laurent.importer.command;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.error.ImportError;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.function.Consumer;

public abstract class ImporterLigne extends ImportError {
    protected Consumer<ErrorMessage> errorConsumer;

    public ImporterLigne onError(Consumer<ErrorMessage> consumer) {
        this.errorConsumer = consumer;
        return this;
    }

    public abstract Validator<String, ErrorMessage> validator(QuestionReponseImport ligneImport, Question question);
}
