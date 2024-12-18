package ru.liga.parcel.controller;

import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.parcel.manager.CommandManager;

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
        2. Enter file path with parcels
        3. Press "Ctrl + C" for exit from app.
        """);

        var scanner = new Scanner(System.in);
        var line = scanner.nextLine();
        var mode = commandManager.selectModeCommand(line);

        line = scanner.nextLine();
        commandManager.printCommand(line, mode);
    }
}
