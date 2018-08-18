package com.laurent.importer.api.command;

@FunctionalInterface
public interface UpdateCommand {
    void apply();
}
