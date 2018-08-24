package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.command.ImporterLigne;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;

public class NoCheckReponseLigne extends ImporterLigne {

    @Override
    public Validator<String, ErrorMessage> validator(QuestionReponseImport ligneImport, Question question) {
        return Validator.of(ligneImport.getReponse());
    }
}
