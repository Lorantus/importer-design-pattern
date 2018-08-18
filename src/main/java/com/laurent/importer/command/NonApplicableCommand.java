package com.laurent.importer.command;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class NonApplicableCommand extends ImporterQuestionCommand {
    private static final String NON_APPLICABLE = "/";

    public NonApplicableCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        return isLaisser(ligneImport) ?
                Optional.empty() :
                nextAction(
                    ImporterQuestionCommand.createWithContext(getContext())
                        .addCommand(NonConcerneCommand::new)
                        .process(ligneImport, question));
    }

    private boolean isLaisser(QuestionReponseImport ligne) {
        return ligne.getReponse().trim().isEmpty()
                || ligne.getCommentaire().trim().isEmpty()
                || NON_APPLICABLE.equals(ligne.getReponse());
    }
}
