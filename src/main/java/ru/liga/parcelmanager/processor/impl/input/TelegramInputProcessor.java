package ru.liga.parcelmanager.processor.impl.input;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.parcelmanager.command.CommandInvoker;
import ru.liga.parcelmanager.processor.InputProcessor;

@RequiredArgsConstructor
public class TelegramInputProcessor extends TelegramLongPollingBot implements InputProcessor {

    private final CommandInvoker commandInvoker;

    @Override
    public String getBotUsername() {
        return "ParcelManagerBot";
    }

    @Override
    public String getBotToken() {
        // TODO в конфиг
        return "7928876755:AAFRE-kU_dqFmjbH6g603rqNFjIsiD6eYK8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            commandInvoker.invoke(messageText);
        }
    }

    @Override
    public void listen() {

    }
}
