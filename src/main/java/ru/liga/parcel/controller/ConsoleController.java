package ru.liga.parcel.controller;

import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.parcel.manager.CommandManager;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.model.enums.OutputType;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final CommandManager commandManager;

    public void start() {
        log.info(
        """
        Program parcel loader. Version 1.0.0
        How program works:
        1. Choose loading mode:
            - "one by one" - 1 truck 1 parcel
            - "loading to capacity" - load every truck to full capacity
            - "uniform" - uniform loading to every truck
        2. Choose number of trucks or press "N" for default behavior
        3. Choose output type:
            - "console" - print to console
            - "json" - write trucks structure to json file
        4. Enter file path with parcels
        5. Press "Ctrl + C" for exit from app.
        """);

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        LoadingMode mode = commandManager.selectModeCommand(line);

        line = scanner.nextLine();
        Integer numberOfTrucks = commandManager.selectNumberOfTrucks(line);

        line = scanner.nextLine();
        OutputType outputType = commandManager.selectOutputType(line);

        line = scanner.nextLine();
        commandManager.importCommand(line, mode, numberOfTrucks, outputType);
    }
}
