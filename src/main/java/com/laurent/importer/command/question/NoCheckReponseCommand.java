package com.laurent.importer.command.question;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.command.ImporterQuestionCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class NoCheckReponseCommand extends ImporterQuestionCommand {
    public NoCheckReponseCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        return Optional.of(() -> question.setReponse(ligneImport.getReponse()));
    }
}
