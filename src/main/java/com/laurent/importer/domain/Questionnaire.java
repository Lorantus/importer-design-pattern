package com.laurent.importer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "node")
public class Questionnaire {
    private final String node;
    private final Map<UUID, Question> questions;
    private final Map<UUID, Reponse> reponses;

    public Optional<Reponse> getReponse(Question question) {
        return Optional.ofNullable(reponses.get(question.getId()));
    }
}
