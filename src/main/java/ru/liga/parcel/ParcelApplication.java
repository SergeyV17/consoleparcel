package ru.liga.parcel;

import ru.liga.parcel.controller.ConsoleController;
import ru.liga.parcel.manager.CommandManager;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.PrintingService;
import ru.liga.parcel.util.TxtParser;
import ru.liga.parcel.util.TxtReader;
import ru.liga.parcel.validation.FileValidator;

public class ParcelApplication {
    public static void main(String[] args) {
        var consoleController = new ConsoleController(
                new CommandManager(new TxtParser(new TxtReader(), new FileValidator()),
                                new ParcelLoadingService(),
                                new PrintingService()));
        consoleController.start();
    }
}
