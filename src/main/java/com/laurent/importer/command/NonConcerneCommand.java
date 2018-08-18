package com.laurent.importer.command;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class NonConcerneCommand extends ImporterQuestionCommand {
    private static final String NON_CONCERNE = "Non concerné";

    public NonConcerneCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        return NON_CONCERNE.equals(ligneImport.getReponse()) ?
                nextAction(() -> question.setNonConcerne(true)) :
                nextAction(() ->
                        ImporterQuestionCommand.createWithContext(getContext())
                            .addCommand(TypeQuestionValidatorCommand::new)
                            .addCommand(CommentaireCommand::new)
                            .process(ligneImport, question));
    }
}
