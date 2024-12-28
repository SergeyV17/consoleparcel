package ru.liga.parcelmanager.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.model.enums.ProgramMode;
import ru.liga.parcelmanager.service.InputCommandService;
import ru.liga.parcelmanager.service.OutputService;

import java.util.List;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private static final String TRUCKS_FILE_NAME = "trucks";

    private final Scanner scanner;
    private final InputCommandService inputCommandService;
    private final OutputService outputService;

    public void start() {
        log.info(
        """
        Program parcel loader. Version 1.0.0
        How program works:
        1. Choose program mode:
            - "loading trucks" - load parcels into trucks
            - "unloading trucks" - unload parcels from trucks

        2. Program mode behaviors:
            Loading trucks:
            1. Choose loading mode:
                - "one by one" - 1 truck 1 parcel
                - "loading to capacity" - load every truck to full capacity
                - "uniform" - uniform loading to every truck
            2. Choose number of trucks or press "N" for default behavior
            3. Choose output type:
                - "console" - print to console
                - "json" - write trucks structure to json file
            4. Enter file path with parcels

            Unloading trucks:
            1. Enter json file path with trucks
        3. Press "Ctrl + C" or "exit" for exit from app.
        """);

        startScenario();
    }

    private void startScenario() {
        do {
            try {
                log.info("Select program mode: \"loading trucks\", \"unloading trucks\" or press \"exit\"");
                String command = scanner.nextLine();

                if (inputCommandService.isExitCommand(command)) {
                    log.info("Goodbye!");
                    return;
                }

                if (inputCommandService.isSelectProgramModeCommand(command)) {
                    ProgramMode programMode = inputCommandService.selectProgramModeCommand(command);
                    log.info("Selected program mode: {}", programMode);

                    Output<?> output = switch (programMode) {
                        case LOADING_TRUCKS -> loadTrucks();
                        case UNLOADING_TRUCKS -> unloadTrucks();
                    };
                    outputService.sendValuesToOutput(output);
                    log.info("Output completed");
                    continue;
                }

                log.error("Invalid command: {}. Try again or press \"exit\"", command);
            }
            catch (Exception e) {
                log.error("An exception occurred {}. Please try again", e.getMessage());
            }
        }
        while(true);
    }

    private Output<String> unloadTrucks() {
        log.info("Enter input json file path: ");
        String jsonFileLine = scanner.nextLine();
        return new Output<>(TRUCKS_FILE_NAME, OutputType.TXT, inputCommandService.unloadTrucksCommand(jsonFileLine));
    }

    private Output<Truck> loadTrucks() {
        log.info("Select loading mode: \"one by one\", \"loading to capacity\", \"uniform\"");
        String loadingModeLine = scanner.nextLine();
        LoadingMode loadingMode = inputCommandService.selectLoadingModeCommand(loadingModeLine);
        log.info("Selected loading mode: {}", loadingMode);

        log.info("Select number of trucks or press \"N\" for default behavior: ");
        String numberOfTrucksLine = scanner.nextLine();
        Integer numberOfTrucks = inputCommandService.selectNumberOfTrucksCommand(numberOfTrucksLine);

        log.info("Select output type: \"console\", \"json\"");
        String outputTypeLine = scanner.nextLine();
        OutputType outputType = inputCommandService.selectOutputTypeCommand(outputTypeLine);

        log.info("Enter input file path: ");
        String inputFilePath = scanner.nextLine();
        List<Truck> trucks = inputCommandService.loadTrucksCommand(
                inputFilePath,
                loadingMode,
                numberOfTrucks);

        return new Output<>(TRUCKS_FILE_NAME, outputType, trucks);
    }
}
