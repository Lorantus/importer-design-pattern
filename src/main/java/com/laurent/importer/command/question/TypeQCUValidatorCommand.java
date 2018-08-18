package com.laurent.importer.command.question;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.command.ImporterQuestionCommand;
import com.laurent.importer.compoments.ChoixLibelleComparator;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.singletonList;

public class TypeQCUValidatorCommand extends ImporterQuestionCommand {
    public TypeQCUValidatorCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        ChoixLibelleComparator choixLibelleComparator = ChoixLibelleComparator.getInstance(singletonList(ligneImport.getReponse()));
        return question.getChoix().stream()
                .filter(choix -> choix.getLibelle().equals(ligneImport.getReponse()))
                .findFirst()
                .map(Choix::getId)
                .map(UUID::toString)
                .map(id -> nextAction(() -> question.setReponse(id)))
                .orElse(error(ligneImport, "Cette réponse n'est pas valide"));
    }
}
