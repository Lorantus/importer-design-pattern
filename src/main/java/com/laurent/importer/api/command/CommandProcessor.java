package com.laurent.importer.api.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class CommandProcessor<C, R, T> implements Command<R, T> {
    private final C context;
    private final List<Function<C, CommandProcessor<C, R, T>>> commands;

    protected CommandProcessor(C context) {
        this.context = context;
        commands = new ArrayList<>();
    }

    protected C getContext() {
        return context;
    }

    public CommandProcessor<C, R, T> addCommand(Function<C, CommandProcessor<C, R, T>> command) {
        this.commands.add(command);
        return this;
    }

    public UpdateCommand process(R ligneImport, T ligneModel) {
        return () -> createCommands(commands)
                .map(command -> command.execute(ligneImport, ligneModel))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(UpdateCommand::apply);
    }

    private Stream<CommandProcessor<C, R, T>> createCommands(Collection<Function<C, CommandProcessor<C, R, T>>> importCommands) {
        return importCommands.stream()
                .map(importCommand -> importCommand.apply(context));
    }

    protected Optional<UpdateCommand> nextAction(UpdateCommand updateCommand) {
        return Optional.of(updateCommand);
    }

    @Override
    public Optional<UpdateCommand> execute(R ligneImport, T ligneModel) {
        createCommands(commands)
                .forEach(command -> command.execute(ligneImport, ligneModel));
        return Optional.empty();
    }
}
