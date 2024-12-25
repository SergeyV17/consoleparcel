package ru.liga.parcel;

import ru.liga.parcel.controller.ConsoleController;
import ru.liga.parcel.factory.TruckFactory;
import ru.liga.parcel.manager.CommandManager;
import ru.liga.parcel.manager.LoadingProcessorManager;
import ru.liga.parcel.manager.OutputProcessorManager;
import ru.liga.parcel.processor.loading.FullCapacityLoadingProcessor;
import ru.liga.parcel.processor.loading.OneByOneLoadingProcessor;
import ru.liga.parcel.processor.loading.UniformLoadingProcessor;
import ru.liga.parcel.processor.loading.shared.ParcelRowsGenerator;
import ru.liga.parcel.processor.output.JsonOutputProcessor;
import ru.liga.parcel.processor.output.TxtOutputProcessor;
import ru.liga.parcel.service.OutputService;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.processor.output.ConsoleOutputProcessor;
import ru.liga.parcel.service.TruckUnloadingService;
import ru.liga.parcel.util.JsonParser;
import ru.liga.parcel.util.TxtParser;
import ru.liga.parcel.util.TxtReader;
import ru.liga.parcel.validation.FileValidator;
import ru.liga.parcel.validation.NumberOfTrucksValidator;

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
                new CommandManager(
                        new TxtParser(new TxtReader(), new FileValidator()),
                        new JsonParser(),
                        new ParcelLoadingService(loadingProcessorManager),
                        new TruckUnloadingService(),
                        new OutputService(outputProcessorManager)));
    }
}
