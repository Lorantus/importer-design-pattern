package com.laurent.importer.comparator;

import com.laurent.importer.domain.Choix;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ChoixLibelleComparator {
    private final Map<IgnoreCaseComparator, Choix> choix;

    public ChoixLibelleComparator(Collection<Choix> choix) {
        this.choix = choix.stream()
                .collect(toMap(
                        theChoix -> new IgnoreCaseComparator(theChoix.getLibelle()),
                        Function.identity()
                ));
    }

    public Choix get(String libelle) {
        return choix.get(new IgnoreCaseComparator(libelle));
    }
}
