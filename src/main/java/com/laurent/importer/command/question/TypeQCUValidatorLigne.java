package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;

import static com.laurent.importer.error.ImportLigneErrorMessage.createMessage;
import static java.util.Collections.singletonList;

public class TypeQCUValidatorLigne extends TypeQuestionAChoixValidatorLigne {

    @Override
    public Validator<String, ErrorMessage> validator(QuestionReponseImport ligneImport, Question question) {
        return Validator.<String, ErrorMessage>of(mapChoixQuestion(singletonList(ligneImport.getReponse()), question))
                .onError(errorConsumer)
                .validate(
                        s -> !s.isEmpty(),
                        () -> createMessage(ligneImport, "Cette réponse n'est pas valide."));
    }
}
