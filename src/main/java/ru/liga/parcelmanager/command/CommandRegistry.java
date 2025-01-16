package ru.liga.parcelmanager.command;

import java.util.Map;

public class CommandRegistry {

    private final Map<String, Command> commands;

    public CommandRegistry(Map<String, Command> commands) {
        this.commands = commands;
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
