package com.laurent.importer.command;

import com.laurent.importer.ImportQuestionErrorMessage;
import com.laurent.importer.api.command.CommandProcessor;
import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.compoments.QuestionComparator;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.List;
import java.util.Optional;

public class ImporterQuestionsCommand extends CommandProcessor<ImportContext, QuestionReponseImport, List<QuestionReponse>> {
    private ImporterQuestionsCommand(ImportContext context) {
        super(context);
    }

    public static ImporterQuestionsCommand createWithContext(ImportContext importContext) {
        return new ImporterQuestionsCommand(importContext);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, List<QuestionReponse> questionReponses) {
        QuestionComparator questionComparator = QuestionComparator.getInstance(ligneImport.getQuestion());
        return questionReponses.stream()
                .filter(questionReponse -> questionComparator.has(questionReponse.getQuestion()))
                .findFirst()
                .map(questionReponse ->
                        nextAction(ImporterQuestionCommand.createWithContext(getContext())
                                .process(ligneImport, questionReponse)))
                .orElse(Optional.of(() ->
                        getContext().addErrorMessage(
                                new ImportQuestionErrorMessage(ligneImport.getLigne(), ligneImport.getQuestion(), "Cette question n'existe pas."))));
    }
}
