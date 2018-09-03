package com.laurent.importer;

import com.laurent.importer.api.error.ErrorMessage;
import com.laurent.importer.api.validator.Validator;
import com.laurent.importer.model.ReponseValidee;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collector;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImportReponseCollector {

    public static Collector<Optional<ReponseValidee>, Collection<ReponseValidee>, Collection<ReponseValidee>> doublonReponseValidatorCollector(
            BiFunction<ReponseValidee, ReponseValidee, Validator<ReponseValidee, ErrorMessage>> validator) {
        return Collector.of(
                HashSet::new,
                (acc, reponseValideeOp) -> reponseValideeOp.ifPresent(reponseValidee -> {
                    boolean notPresent = acc.stream()
                            .filter(reponseValideeExistante -> reponseValideeExistante.isSameQuestion(reponseValidee.getQuestion()))
                            .map(reponseValideeExistante -> validator.apply(reponseValideeExistante, reponseValidee))
                            .noneMatch(Validator::isPresent);
                    if (notPresent) {
                        acc.add(reponseValidee);
                    }
                }),
                (acc1, acc2) -> {
                    acc2.addAll(acc1);
                    return acc2;
                }
        );
    }
}
