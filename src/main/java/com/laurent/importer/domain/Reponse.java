package com.laurent.importer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reponse {
    private final UUID id;
    private final UUID question;
    private String reponse;
    private String commentaire;
    private boolean nonConcerne;
}
