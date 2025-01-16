package ru.liga.parcelmanager.command;

import ru.liga.parcelmanager.exceptions.CommandArgumentNotFoundException;

public abstract class Command<TResult> {

    public abstract TResult execute(String[] args);

    public String getArgumentValue(String[] args, String option) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option)) {
                return args[i + 1];
            }
        }

        throw new CommandArgumentNotFoundException(String.format("Argument %s not found", option));
    }
}
