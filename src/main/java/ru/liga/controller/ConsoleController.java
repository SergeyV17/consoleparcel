package ru.liga.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.model.enums.LoadingMode;
import ru.liga.service.ParcelLoadingService;
import ru.liga.service.PrintingService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final ParcelLoadingService parcelLoadingService;
    private final PrintingService printingService;

    private final Pattern IMPORT_COMMAND_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern LOADING_MODE_PATTERN = Pattern.compile("(loading to capacity|one by one)");

    private LoadingMode mode;

    public void listen() {
        var scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            var command = scanner.nextLine();
            if (command.equals("exit")) {
                System.exit(0);
            }

            if (LOADING_MODE_PATTERN.matcher(command).matches()) {
                SelectModeCommand(command);
                continue;
            }

            if (mode == LoadingMode.NONE) {
                System.out.println("First select the loading mode" + command);
                log.error("User doesn't select loading mode" + command);
                continue;
            }

            Matcher importCommandMatcher = IMPORT_COMMAND_PATTERN.matcher(command);
            if (importCommandMatcher.matches()) {
                if (ImportCommand(importCommandMatcher)) continue;
                return;
            }

            System.out.println("Incorrect command: " + command);
            log.error("User enter incorrect command: {}", command);
        }
    }

    private boolean ImportCommand(Matcher importCommandMatcher) {
        String filePath = importCommandMatcher.group(1);

        log.info("Start loading parcels into trucks...");
        var cargosWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(filePath, mode);
        if (cargosWithinTrucks.isEmpty()) {
            System.out.println("Valid parcels not found. Check the file: " + filePath);
            log.error("File {} don't have valid parcels", filePath);
            return true;
        }

        log.info("Print trucks into console...");
        printingService.PrintTrucks(cargosWithinTrucks);

        log.info("Loading parcels into trucks completed");
        return false;
    }

    private void SelectModeCommand(String command) {
        switch (command) {
            case "loading to capacity" -> mode = LoadingMode.LOADING_TO_CAPACITY;
            case "one by one" -> mode = LoadingMode.ONE_BY_ONE;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        }

        System.out.println("Selected mode: " + mode);
        log.info("Selected mode: {}", mode);
    }
}
