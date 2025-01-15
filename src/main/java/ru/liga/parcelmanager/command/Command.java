package ru.liga.parcelmanager.command;

public abstract class Command {

    public abstract void execute(String[] args);

    public String getOptionalValue(String[] args, String option) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option)) {
                return args[i + 1];
            }
        }

        return null;
    }
}
