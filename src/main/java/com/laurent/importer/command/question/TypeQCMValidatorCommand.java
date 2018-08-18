package com.laurent.importer.command.question;

import com.laurent.importer.api.command.UpdateCommand;
import com.laurent.importer.command.ImporterQuestionCommand;
import com.laurent.importer.compoments.ChoixLibelleComparator;
import com.laurent.importer.compoments.ImportContext;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.QuestionReponse;
import com.laurent.importer.model.QuestionReponseImport;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TypeQCMValidatorCommand extends ImporterQuestionCommand {
    public TypeQCMValidatorCommand(ImportContext context) {
        super(context);
    }

    @Override
    public Optional<UpdateCommand> execute(QuestionReponseImport ligneImport, QuestionReponse question) {
        List<String> reponsesChoix = asList(ligneImport.getReponse().split("\n"));
        ChoixLibelleComparator choixLibelleComparator = ChoixLibelleComparator.getInstance(reponsesChoix);
        List<String> choixes = question.getChoix().stream()
                .filter(choix -> choixLibelleComparator.has(choix.getLibelle()))
                .map(Choix::getId)
                .map(UUID::toString)
                .collect(toList());

        return choixes.size() == reponsesChoix.size() ?
                nextAction(() -> question.setReponse(String.join(",", choixes))) :
                error(ligneImport, "Cette réponse n'est pas valide");
    }
}
