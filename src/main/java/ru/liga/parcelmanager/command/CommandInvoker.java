package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.exceptions.NotFoundException;

@RequiredArgsConstructor
public class CommandInvoker {

    public static final int PARTS_SPLITTING_NUMBER = 2;
    private final CommandRegistry registry;

    public void invoke(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid input: %s", input));
        }

        String[] parts = input.split(" ", PARTS_SPLITTING_NUMBER);
        String commandName = parts[0];
        String[] args = parts.length > 1 ? parts[1].split(" ") : new String[0];

        Command command = registry.getCommand(commandName);
        if (command != null) {
            command.execute(args);
        }

        throw new NotFoundException(String.format("Command %s not found", command));
    }

}
