package ru.liga.parcelmanager.controller;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.processor.impl.input.ConsoleInputProcessor;
import ru.liga.parcelmanager.processor.impl.input.TelegramInputProcessor;

@RequiredArgsConstructor
public class ParcelController {

    private final ConsoleInputProcessor consoleInputProcessor;
    private final TelegramInputProcessor telegramInputProcessor;

    public void startListening() {
        telegramInputProcessor.listen();
        consoleInputProcessor.listen();
    }
}
