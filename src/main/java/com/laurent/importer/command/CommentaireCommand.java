package com.laurent.importer.command;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;

public class CommentaireCommand extends ImporterQuestionCommand {
    public CommentaireCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        return nextAction(() -> question.setCommentaire(ligneImport.getCommentaire()));
    }
}