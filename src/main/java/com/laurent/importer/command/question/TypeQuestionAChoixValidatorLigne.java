package com.laurent.importer.command.question;

import com.laurent.importer.command.ImporterLigne;
import com.laurent.importer.comparator.ChoixLibelleComparator;
import com.laurent.importer.domain.Choix;
import com.laurent.importer.domain.Question;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public abstract class TypeQuestionAChoixValidatorLigne extends ImporterLigne {

    protected String mapChoixQuestion(List<String> reponsesChoix, Question question) {
        ChoixLibelleComparator choixLibelleComparator = new ChoixLibelleComparator(question.getChoixes());
        List<String> choixes = reponsesChoix.stream()
                .map(choixLibelleComparator::get)
                .filter(Objects::nonNull)
                .map(Choix::getId)
                .map(UUID::toString)
                .collect(toList());

        if (choixes.size() == reponsesChoix.size()) {
            return String.join(",", choixes);
        }
        return "";
    }
}
