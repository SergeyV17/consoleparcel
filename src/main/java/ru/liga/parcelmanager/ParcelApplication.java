package ru.liga.parcelmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.parcelmanager.command.CommandInvoker;
import ru.liga.parcelmanager.command.CommandRegistry;
import ru.liga.parcelmanager.command.CreateCommand;
import ru.liga.parcelmanager.command.DeleteCommand;
import ru.liga.parcelmanager.command.EditCommand;
import ru.liga.parcelmanager.command.FindCommand;
import ru.liga.parcelmanager.command.LoadCommand;
import ru.liga.parcelmanager.command.UnloadCommand;
import ru.liga.parcelmanager.command.consts.CommandNames;
import ru.liga.parcelmanager.controller.ParcelController;
import ru.liga.parcelmanager.factory.ParcelFactory;
import ru.liga.parcelmanager.processor.impl.input.ConsoleInputProcessor;
import ru.liga.parcelmanager.processor.impl.input.TelegramInputProcessor;
import ru.liga.parcelmanager.service.ParcelService;
import ru.liga.parcelmanager.validation.ParcelValidator;

@SpringBootApplication
public class ParcelApplication {

    public static void main(String[] args) {

        SpringApplication.run(ParcelApplication.class, args);

        // TODO добавить посылки по умолчанию
//        ParcelRepository parcelRepository = new ParcelRepository(new ArrayList<>());

        CommandInvoker commandInvoker = new CommandInvoker(commandRegistry);

        ParcelController parcelController = new ParcelController(
                new ConsoleInputProcessor(commandInvoker),
                new TelegramInputProcessor(commandInvoker));

        parcelController.startListening();
    }
}
