package com.laurent.importer.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DataSource {
    private final List<String[]> rows;
}
