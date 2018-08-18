package com.laurent.importer.command;

import com.laurent.importer.ImportQuestionErrorMessage;
import com.laurent.importer.api.command.CommandProcessor;
import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class ImporterQuestionCommand extends CommandProcessor<ImportContext, QuestionReponseImport, QuestionReponse> {
    protected ImporterQuestionCommand(ImportContext context) {
        super(context);
        addCommand(NonApplicableCommand::new);
    }

    public static ImporterQuestionCommand createWithContext(ImportContext context) {
        return new ImporterQuestionCommand(context);
    }

    protected Optional<UpdateCommand> error(QuestionReponseImport ligne, String message) {
        return Optional.of(() -> getContext().addErrorMessage(
                new ImportQuestionErrorMessage(ligne.getLigne(), ligne.getQuestion(), message)));
    }
}
