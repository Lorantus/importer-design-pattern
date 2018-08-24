package com.laurent.importer.api;

import java.util.List;

public interface DataSource {
    List<String[]> getRows();
}
