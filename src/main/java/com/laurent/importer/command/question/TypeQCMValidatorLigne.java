package com.laurent.importer.command.question;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.domain.Question;
import com.laurent.importer.model.QuestionReponseImport;

import static com.laurent.importer.error.ImportLigneErrorMessage.createMessage;
import static java.util.Arrays.asList;

public class TypeQCMValidatorLigne extends TypeQuestionAChoixValidatorLigne {

    @Override
    public Validator<String, ErrorMessage> validator(QuestionReponseImport ligneImport, Question question) {
        return Validator.<String, ErrorMessage>of(mapChoixQuestion(asList(ligneImport.getReponse().split("\n")), question))
                .onError(errorConsumer)
                .validate(
                        s -> !s.isEmpty(),
                        () -> createMessage(ligneImport, "Cette réponse n'est pas valide."));
    }
}
