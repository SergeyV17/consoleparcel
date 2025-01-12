package ru.liga.parcelmanager.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.parcelmanager.service.InputCommandService;

@Slf4j
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramLongPollingBot bot;
    private final InputCommandService inputCommandService;

    public TelegramController(String botToken) {
        bot = new TelegramLongPollingBot() {
            @Override
            public void onUpdateReceived(Update update) {
                if (update.hasMessage()) {
                    String messageText = update.getMessage().getText();

                    if (inputCommandService.isExitCommand(messageText)) {
                        System.exit(0);
                    }

                    System.out.println("Received message: " + messageText);
                }
            }

            @Override
            public String getBotUsername() {
                return "ParcelManagerBot";
            }

            @Override
            public String getBotToken() {
                return botToken;
            }
        };
    }

    // TODO SERGEY VLASENKO а надо ли что то отправлять?
    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}
