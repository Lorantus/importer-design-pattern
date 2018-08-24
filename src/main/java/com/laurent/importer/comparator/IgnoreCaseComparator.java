package com.laurent.importer.comparator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IgnoreCaseComparator {
    private final String libelle;

    public boolean is(String libelle) {
        return this.equals(new IgnoreCaseComparator(libelle));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IgnoreCaseComparator that = (IgnoreCaseComparator) o;
        return libelle != null ? libelle.equalsIgnoreCase(that.libelle) : that.libelle == null;
    }

    @Override
    public int hashCode() {
        return libelle != null ? libelle.toUpperCase().hashCode() : 0;
    }
}
