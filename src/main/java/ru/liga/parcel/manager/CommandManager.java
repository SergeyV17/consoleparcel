package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.PrintingService;
import ru.liga.parcel.util.TxtParser;

import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class CommandManager {
    private final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern SELECT_MODE_PATTERN = Pattern.compile("(loading to capacity|one by one)");

    private final TxtParser txtParser;
    private final ParcelLoadingService parcelLoadingService;
    private final PrintingService printingService;

    public void importCommand(String command, LoadingMode mode) {
        var matcher = TXT_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }

        var filePath = matcher.group(1);

        log.info("Start loading parcels into trucks...");
        var parcels = txtParser.parseCargoFromFile(filePath);
        if (parcels.isEmpty()) {
            throw new IllegalArgumentException("Parcels not found in " + filePath);
        }
        var cargosWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(parcels, mode);

        log.info("Print trucks into console...");
        printingService.PrintTrucks(cargosWithinTrucks);

        log.info("Loading parcels into trucks completed");
    }

    public LoadingMode selectModeCommand(String command) {
        if (!SELECT_MODE_PATTERN.matcher(command).matches()) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }

        var selectedMode = LoadingMode.NONE;
        switch (command) {
            case "loading to capacity" -> selectedMode = LoadingMode.LOADING_TO_CAPACITY;
            case "one by one" -> selectedMode = LoadingMode.ONE_BY_ONE;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        }

        log.info("Selected mode: {}", selectedMode);
        return selectedMode;
    }
}
