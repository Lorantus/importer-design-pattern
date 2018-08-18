package com.laurent.importer.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class LecteurImport<T> {
    private DataSource datas;

    protected LecteurImport(DataSource datas) {
        this.datas = datas;
    }

    public List<T> parse() {
        int ligne = 1;
        List<T> list = new ArrayList<>();
        for(String[] row: datas.getRows()) {
            list.add(importData().apply(row, ligne++));
        }
        return list;
    }

    protected abstract BiFunction<String[], Integer, T> importData();
}
