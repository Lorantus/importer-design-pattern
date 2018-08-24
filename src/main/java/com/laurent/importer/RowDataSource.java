package com.laurent.importer;

import com.laurent.importer.api.DataSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RowDataSource implements DataSource {
    private final List<String[]> rows;
}
