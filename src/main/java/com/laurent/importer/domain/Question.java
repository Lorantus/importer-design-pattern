package com.laurent.importer.domain;

import com.laurent.importer.model.TypeQuestion;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Question {
    private final UUID id;
    private final TypeQuestion typeQuestion;
    private final String question;
    private List<Choix> choixes;
}
