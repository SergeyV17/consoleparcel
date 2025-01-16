package ru.liga.parcelmanager.processor.impl.input;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.parcelmanager.command.CommandInvoker;
import ru.liga.parcelmanager.properties.TelegramProperties;

@Slf4j
@RequiredArgsConstructor
public class TelegramInputProcessor extends TelegramLongPollingBot {

    private final CommandInvoker commandInvoker;
    private final TelegramProperties telegramProperties;

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText();
            commandInvoker.invoke(messageText);
        }
    }

    @PostConstruct
    public void listen() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error("An exception occurred when registering bot {}", e.getMessage());

        }
    }
}
