package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.command.ImporterLigne;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;

import static com.laurent.importer.error.ImportLigneErrorMessage.createMessage;

public class TypeNumericValidatorLigne extends ImporterLigne {

    private boolean isNumeric(String value) {
        return value.matches("[0-9]");
    }

    @Override
    public Validator<String, ErrorMessage> validator(QuestionReponseImport ligneImport, Question question) {
        String reponse = ligneImport.getReponse();
        return Validator.<String, ErrorMessage>of(reponse)
                .onError(errorConsumer)
                .validate(
                        this::isNumeric,
                        () -> createMessage(ligneImport, "Cette question nécessite un entier en réponse"));
    }
}
