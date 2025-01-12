package ru.liga.parcelmanager;

import ru.liga.parcelmanager.controller.ConsoleController;
import ru.liga.parcelmanager.controller.TelegramController;
import ru.liga.parcelmanager.factory.TruckFactory;
import ru.liga.parcelmanager.processor.impl.loading.shared.NumberOfTrucksCalculator;
import ru.liga.parcelmanager.service.InputCommandService;
import ru.liga.parcelmanager.service.LoadingProcessorService;
import ru.liga.parcelmanager.service.OutputService;
import ru.liga.parcelmanager.processor.impl.loading.FullCapacityLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.loading.OneByOneLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.loading.UniformLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.loading.shared.ParcelRowsGenerator;
import ru.liga.parcelmanager.processor.impl.output.JsonOutputProcessor;
import ru.liga.parcelmanager.processor.impl.output.TxtOutputProcessor;
import ru.liga.parcelmanager.processor.impl.output.ConsoleOutputProcessor;
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

        // TODO SERGEY VLASENKO перенести в конфиг
        var telegramController = new TelegramController("7928876755:AAFRE-kU_dqFmjbH6g603rqNFjIsiD6eYK8");

        // TODO SERGEY VLASENKO старая реализация через консоль, если что выпилить
//        var consoleController = createConsoleController();
//        consoleController.start();
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
                        loadingProcessorService,
                        new TruckUnloadingService()),
                new OutputService(
                        new ConsoleOutputProcessor(),
                        new JsonOutputProcessor(),
                        new TxtOutputProcessor()
                ));
    }
}
