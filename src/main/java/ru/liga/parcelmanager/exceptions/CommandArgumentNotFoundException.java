package ru.liga.parcelmanager.exceptions;

public class CommandArgumentNotFoundException extends RuntimeException {

    public CommandArgumentNotFoundException(String message) {
        super(message);
    }
}
