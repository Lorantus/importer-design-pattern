package com.laurent.importer;

import com.laurent.importer.comparator.QuestionLibelleComparator;
import com.laurent.importer.domain.Question;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class LibelleQuestionFinder {
    private final Map<QuestionLibelleComparator, Question> questions;

    public LibelleQuestionFinder(Collection<Question> questions) {
        this.questions = questions.stream()
                .collect(toMap(
                        question -> new QuestionLibelleComparator(question.getQuestion()),
                        Function.identity()));
    }

    public Optional<Question> getQuestion(String libelle) {
        return Optional.ofNullable(questions.get(new QuestionLibelleComparator(libelle)));
    }
}
