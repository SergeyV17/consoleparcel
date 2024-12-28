package ru.liga.parcelmanager;

import ru.liga.parcelmanager.controller.ConsoleController;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.processor.impl.shared.NumberOfTrucksCalculator;
import ru.liga.parcelmanager.service.InputCommandService;
import ru.liga.parcelmanager.service.LoadingProcessorService;
import ru.liga.parcelmanager.service.OutputService;
import ru.liga.parcelmanager.processor.impl.FullCapacityLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.OneByOneLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.UniformLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.shared.ParcelRowsGenerator;
import ru.liga.parcelmanager.processor.impl.JsonOutputProcessor;
import ru.liga.parcelmanager.processor.impl.TxtOutputProcessor;
import ru.liga.parcelmanager.service.ParcelLoadingService;
import ru.liga.parcelmanager.processor.impl.ConsoleOutputProcessor;
import ru.liga.parcelmanager.service.TruckUnloadingService;
import ru.liga.parcelmanager.util.JsonParser;
import ru.liga.parcelmanager.util.TxtParser;
import ru.liga.parcelmanager.util.TxtReader;
import ru.liga.parcelmanager.service.CommandValidationService;
import ru.liga.parcelmanager.service.FileValidationService;
import ru.liga.parcelmanager.service.TruckValidationService;

import java.util.Scanner;

public class ParcelApplication {

    public static void main(String[] args) {
        var consoleController = createConsoleController();
        consoleController.start();
    }

    private static ConsoleController createConsoleController() {
        LoadingProcessorService loadingProcessorService = new LoadingProcessorService(
                new OneByOneLoadingProcessor(new TruckFactory()),
                new FullCapacityLoadingProcessor(new ParcelRowsGenerator(), new TruckFactory()),
                new UniformLoadingProcessor(new ParcelRowsGenerator(), new NumberOfTrucksCalculator(), new TruckFactory()),
                new TruckValidationService()
        );

        return new ConsoleController(
                new Scanner(System.in),
                new InputCommandService(
                        new CommandValidationService(),
                        new TxtParser(new TxtReader(), new FileValidationService()),
                        new JsonParser(),
                        new ParcelLoadingService(loadingProcessorService),
                        new TruckUnloadingService()),
                new OutputService(
                        new ConsoleOutputProcessor(),
                        new JsonOutputProcessor(),
                        new TxtOutputProcessor()
                ));
    }
}
