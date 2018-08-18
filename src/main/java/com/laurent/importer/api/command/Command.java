package com.laurent.importer.api.command;

import java.util.Optional;

@FunctionalInterface
public interface Command<R, T> {
    Optional<UpdateCommand> execute(R ligneImport, T ligneModel);
}
