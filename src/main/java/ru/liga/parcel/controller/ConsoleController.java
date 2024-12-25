package ru.liga.parcel.controller;

import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.parcel.manager.CommandManager;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.model.enums.OutputType;
import ru.liga.parcel.model.enums.ProgramMode;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final CommandManager commandManager;

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
        3. Press "Ctrl + C" for exit from app.
        """);

        Scanner scanner = new Scanner(System.in);

        log.info("Select program mode: \"loading trucks\", \"unloading trucks\"");
        String line = scanner.nextLine();
        ProgramMode programMode = commandManager.selectProgramModeCommand(line);

        switch (programMode) {
            case LOADING_TRUCKS:
                log.info("Select loading mode: \"one by one\", \"loading to capacity\", \"uniform\"");
                line = scanner.nextLine();
                LoadingMode loadingMode = commandManager.selectLoadingModeCommand(line);

                log.info("Select number of trucks: ");
                line = scanner.nextLine();
                Integer numberOfTrucks = commandManager.selectNumberOfTrucksCommand(line);

                log.info("Select output type: \"console\", \"json\"");
                line = scanner.nextLine();
                OutputType outputType = commandManager.selectOutputTypeCommand(line);

                log.info("Enter input file path: ");
                line = scanner.nextLine();
                commandManager.loadTrucksCommand(line, loadingMode, numberOfTrucks, outputType);
            break;

            case UNLOADING_TRUCKS:
                log.info("Enter input json file path: ");
                line = scanner.nextLine();
                commandManager.unloadTrucksCommand(line);
            break;
        }



    }
}
