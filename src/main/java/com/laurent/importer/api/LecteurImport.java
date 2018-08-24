package com.laurent.importer.api;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public abstract class LecteurImport<T> {
    private DataSource datas;

    protected LecteurImport(DataSource datas) {
        this.datas = datas;
    }

    public Stream<T> parse() {
        final AtomicInteger ligne = new AtomicInteger();
        return datas.getRows().stream()
                .map(row -> importData().apply(row, ligne.incrementAndGet()))
                .filter(Optional::isPresent).map(Optional::get);
    }

    protected abstract BiFunction<String[], Integer, Optional<T>> importData();
}
