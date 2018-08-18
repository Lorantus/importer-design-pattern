package com.laurent.importer.api.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator<T, E> {
    private final T o;
    private final List<E> errors = new ArrayList<>();

    private Validator(T o) {
        this.o = o;
    }

    public static <T, E> Validator<T, E> of(T o) {
        return new Validator<T, E>(Objects.requireNonNull(o));
    }

    public Validator<T, E> validate(Predicate<T> predicate, E error) {
        if (!predicate.test(o)) {
            errors.add(error);
        }
        return this;
    }

    public Validator<T, E> validate(Predicate<T> predicate, Supplier<E> error) {
        if (!predicate.test(o)) {
            errors.add(error.get());
        }
        return this;
    }

    public <U> Validator<T, E> validate(Function<T, U> projection, Predicate<U> validation, E error) {
        return validate(projection.andThen(validation::test)::apply, error);
    }

    public <U> Validator<T, E> validate(Function<T, U> projection, Predicate<U> validation, Supplier<E> error) {
        return validate(projection.andThen(validation::test)::apply, error);
    }

    public List<E> getErrors() {
        return errors;
    }

    public Optional<T> get() {
        if (errors.isEmpty()) {
            return Optional.ofNullable(o);
        }
        return Optional.empty();
    }
}
