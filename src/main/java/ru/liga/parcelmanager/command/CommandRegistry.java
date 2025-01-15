package ru.liga.parcelmanager.command;

import java.util.HashMap;

public class CommandRegistry {

    private final HashMap<String, Command> commands = new HashMap<>();

    // пока нет DI, оставляю регистрацию через публичный метод
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }
}
