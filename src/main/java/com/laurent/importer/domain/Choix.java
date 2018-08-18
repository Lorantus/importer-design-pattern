package com.laurent.importer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Choix {
    private final UUID id;
    private final String libelle;
}
