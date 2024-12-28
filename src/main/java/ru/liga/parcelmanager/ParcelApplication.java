package ru.liga.parcelmanager;

import ru.liga.parcelmanager.controller.ConsoleController;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.service.InputCommandService;
import ru.liga.parcelmanager.service.FeatureService;
import ru.liga.parcelmanager.service.LoadingProcessorService;
import ru.liga.parcelmanager.service.OutputProcessorService;
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
import ru.liga.parcelmanager.service.CommandValidationService;
import ru.liga.parcelmanager.service.FileValidationService;
import ru.liga.parcelmanager.service.NumberOfTrucksValidationService;

import java.util.Scanner;

public class ParcelApplication {

    public static void main(String[] args) {
        var consoleController = createConsoleController();
        consoleController.start();
    }

    private static ConsoleController createConsoleController() {
        LoadingProcessorService loadingProcessorManager = new LoadingProcessorService(
                new OneByOneLoadingProcessor(new TruckFactory()),
                new FullCapacityLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator()),
                new UniformLoadingProcessor(new TruckFactory(), new ParcelRowsGenerator()),
                new NumberOfTrucksValidationService()
        );

        OutputProcessorService outputProcessorManager = new OutputProcessorService(
                new ConsoleOutputProcessor(),
                new JsonOutputProcessor(),
                new TxtOutputProcessor()
        );

        return new ConsoleController(
                new FeatureService(
                        new Scanner(System.in),
                        new InputCommandService(
                                new CommandValidationService(),
                                new TxtParser(new TxtReader(), new FileValidationService()),
                                new JsonParser(),
                                new ParcelLoadingService(loadingProcessorManager),
                                new TruckUnloadingService(),
                                new OutputService(outputProcessorManager))));
    }
}