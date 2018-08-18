package com.laurent.importer.command.question;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.command.ImporterQuestionCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class TypeNumericValidatorCommand extends ImporterQuestionCommand {
    public TypeNumericValidatorCommand(ImportContext context) {
        super(context);
    }

    private boolean isNumeric(String value) {
        return value.matches("[0-9]");
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        String reponse = ligneImport.getReponse();
        return isNumeric(reponse) ?
                nextAction(() -> question.setReponse(reponse)) :
                error(ligneImport, "Cette question nécessite un entier en réponse");
    }
}
