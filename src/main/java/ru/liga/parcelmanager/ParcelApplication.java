package ru.liga.parcelmanager;

import ru.liga.parcelmanager.controller.ConsoleController;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.manager.CommandManager;
import ru.liga.parcelmanager.manager.FeatureManager;
import ru.liga.parcelmanager.manager.LoadingProcessorManager;
import ru.liga.parcelmanager.manager.OutputProcessorManager;
import ru.liga.parcelmanager.processor.loading.FullCapacityLoadingProcessor;
import ru.liga.parcelmanager.processor.loading.OneByOneLoadingProcessor;
import ru.liga.parcelmanager.processor.loading.UniformLoadingProcessor;
import ru.liga.parcelmanager.processor.loading.shared.ParcelRowsGenerator;
import ru.liga.parcelmanager.processor.output.JsonOutputProcessor;
import ru.liga.parcelmanager.processor.output.TxtOutputProcessor;
import ru.liga.parcelmanager.service.OutputService;
import ru.liga.parcelmanager.service.ParcelLoadingService;
import ru.liga.parcelmanager.processor.output.ConsoleOutputProcessor;
import ru.liga.parcelmanager.service.TruckUnloadingService;
import ru.liga.parcelmanager.util.JsonParser;
import ru.liga.parcelmanager.util.TxtParser;
import ru.liga.parcelmanager.util.TxtReader;
import ru.liga.parcelmanager.validation.CommandValidator;
import ru.liga.parcelmanager.validation.FileValidator;
import ru.liga.parcelmanager.validation.NumberOfTrucksValidator;

import java.util.Scanner;

public class ParcelApplication {

    public static void main(String[] args) {
        var consoleController = createConsoleController();
        consoleController.start();
    }

    private static ConsoleController createConsoleController() {
        LoadingProcessorManager loadingProcessorManager = new LoadingProcessorManager(
                new OneByOneLoadingProcessor(new TruckFactory()),
                new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator()),
                new UniformLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator()),
                new NumberOfTrucksValidator()
        );

        OutputProcessorManager outputProcessorManager = new OutputProcessorManager(
                new ConsoleOutputProcessor(),
                new JsonOutputProcessor(),
                new TxtOutputProcessor()
        );

        return new ConsoleController(
                new FeatureManager(
                        new Scanner(System.in),
                        new CommandManager(
                                new CommandValidator(),
                                new TxtParser(new TxtReader(), new FileValidator()),
                                new JsonParser(),
                                new ParcelLoadingService(loadingProcessorManager),
                                new TruckUnloadingService(),
                                new OutputService(outputProcessorManager))));
    }
}
