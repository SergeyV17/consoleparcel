package ru.liga.parcelmanager;

import ru.liga.parcelmanager.command.*;
import ru.liga.parcelmanager.command.consts.CommandNames;
import ru.liga.parcelmanager.controller.ParcelController;
import ru.liga.parcelmanager.factory.ParcelFactory;
import ru.liga.parcelmanager.processor.impl.input.ConsoleInputProcessor;
import ru.liga.parcelmanager.processor.impl.input.TelegramInputProcessor;
import ru.liga.parcelmanager.repository.ParcelRepository;
import ru.liga.parcelmanager.service.ParcelService;
import ru.liga.parcelmanager.validation.ParcelValidator;

import java.util.ArrayList;

public class ParcelApplication {

    public static void main(String[] args) {
        // TODO добавить посылки по умолчанию
        ParcelRepository parcelRepository = new ParcelRepository(new ArrayList<>());

        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.registerCommand(CommandNames.CREATE_COMMAND, new CreateCommand(
                new ParcelService(parcelRepository), new ParcelFactory(), new ParcelValidator()));
        commandRegistry.registerCommand(CommandNames.EDIT_COMMAND, new EditCommand());
        commandRegistry.registerCommand(CommandNames.FIND_COMMAND, new FindCommand());
        commandRegistry.registerCommand(CommandNames.LOAD_COMMAND, new LoadCommand());
        commandRegistry.registerCommand(CommandNames.DELETE_COMMAND, new DeleteCommand());
        commandRegistry.registerCommand(CommandNames.UNLOAD_COMMAND, new UnloadCommand());
        commandRegistry.registerCommand(CommandNames.EXIT_COMMAND, new ExitCommand());

        CommandInvoker commandInvoker = new CommandInvoker(commandRegistry);

        ParcelController parcelController = new ParcelController(
                new ConsoleInputProcessor(commandInvoker),
                new TelegramInputProcessor(commandInvoker));

        parcelController.startListening();
    }
}
