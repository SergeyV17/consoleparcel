package ru.liga.parcel;

import ru.liga.parcel.controller.ConsoleController;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.manager.CommandManager;
import ru.liga.parcel.manager.LoadingProcessorManager;
import ru.liga.parcel.processor.FullCapacityLoadingProcessor;
import ru.liga.parcel.processor.OneByOneLoadingProcessor;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.PrintingService;
import ru.liga.parcel.util.TxtParser;
import ru.liga.parcel.util.TxtReader;
import ru.liga.parcel.validation.FileValidator;

public class ParcelApplication {
    public static void main(String[] args) {
        LoadingProcessorManager loadingProcessorManager = new LoadingProcessorManager(
                new FullCapacityLoadingProcessor(new TruckFactory()),
                new OneByOneLoadingProcessor(new TruckFactory())
        );

        var consoleController = new ConsoleController(
                new CommandManager(new TxtParser(new TxtReader(), new FileValidator()),
                                new ParcelLoadingService(loadingProcessorManager),
                                new PrintingService()));
        consoleController.start();
    }
}
