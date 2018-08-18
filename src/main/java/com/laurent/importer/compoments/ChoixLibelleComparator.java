package com.laurent.importer.compoments;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChoixLibelleComparator {
    private static ChoixLibelleComparator THE_INSTANCE;

    private final Collection<String> choix;

    public static ChoixLibelleComparator getInstance(Collection<String> choix) {
        if(THE_INSTANCE == null) {
            THE_INSTANCE = new ChoixLibelleComparator(choix);
        }
        return THE_INSTANCE;
    }

    public boolean has(String libelle) {
        return choix != null && libelle != null &&
                choix.stream()
                    .anyMatch(libelle::equalsIgnoreCase);
    }
}
