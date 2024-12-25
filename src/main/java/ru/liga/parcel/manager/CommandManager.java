package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.PrintingService;
import ru.liga.parcel.util.TxtParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class CommandManager {
    private final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern NUMBER_OF_TRUCKS_PATTERN = Pattern.compile("\\d+");

    private final TxtParser txtParser;
    private final ParcelLoadingService parcelLoadingService;
    private final PrintingService printingService;

    public void importCommand(String command, LoadingMode mode, Integer numberOfTrucks) {
        Matcher matcher = TXT_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }

        String filePath = matcher.group(1);

        log.info("Start loading parcels into trucks...");
        List<String> parcels = txtParser.parseCargoFromFile(filePath);
        if (parcels.isEmpty()) {
            throw new IllegalArgumentException("Parcels not found in " + filePath);
        }

        List<Truck> cargosWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(parcels, mode, numberOfTrucks);

        log.info("Print trucks into console...");
        printingService.PrintTrucks(cargosWithinTrucks);

        log.info("Loading parcels into trucks completed");
    }

    public LoadingMode selectModeCommand(String command) {
        LoadingMode selectedMode;
        switch (command) {
            case "loading to capacity" -> selectedMode = LoadingMode.LOADING_TO_CAPACITY;
            case "one by one" -> selectedMode = LoadingMode.ONE_BY_ONE;
            case "uniform" -> selectedMode = LoadingMode.UNIFORM;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        }

        log.info("Selected mode: {}", selectedMode);
        return selectedMode;
    }

    public Integer selectNumberOfTrucks(String command) {
        if (command.equals("N")) {
            return null;
        }

        Matcher matcher = NUMBER_OF_TRUCKS_PATTERN.matcher(command);
        if (matcher.matches()) {
            Integer numberOfTrucks = Integer.parseInt(command);
            log.info("Selected number of trucks: {}", numberOfTrucks);
            return numberOfTrucks;
        }

        throw new IllegalArgumentException("Invalid number of trucks: " + command);
    }
}
