package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.model.enums.OutputType;
import ru.liga.parcel.model.enums.ProgramMode;
import ru.liga.parcel.service.OutputService;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.TruckUnloadingService;
import ru.liga.parcel.util.JsonParser;
import ru.liga.parcel.util.TxtParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class CommandManager {
    private final Pattern TXT_FILE_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern JSON_FILE_PATTERN = Pattern.compile("(.+\\.json)");
    private final Pattern NUMBER_OF_TRUCKS_PATTERN = Pattern.compile("\\d+");

    private final TxtParser txtParser;
    private final JsonParser jsonParser;
    private final ParcelLoadingService parcelLoadingService;
    private final TruckUnloadingService truckUnloadingService;
    private final OutputService outputService;

    public void loadTrucksCommand(String command, LoadingMode mode, Integer numberOfTrucks, OutputType outputType) {
        Matcher matcher = TXT_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid txt file: " + command);
        }

        String filePath = matcher.group(1);

        log.info("Start loading parcels into trucks...");
        List<String> parcels = txtParser.parseCargoFromFile(filePath);
        if (parcels.isEmpty()) {
            throw new IllegalArgumentException("Parcels not found in " + filePath);
        }

        List<Truck> cargosWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(parcels, mode, numberOfTrucks);

        log.info("Sending trucks to output...");
        outputService.SendTrucksToOutput(cargosWithinTrucks, outputType);

        log.info("Loading parcels into trucks completed");
    }

    public void unloadTrucksCommand(String command) {
        Matcher matcher = JSON_FILE_PATTERN.matcher(command);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid json file: " + command);
        }

        String filePath = matcher.group(1);

        log.info("Start unloading trucks into parcels...");
        List<Truck> trucks = jsonParser.parseTrucksFromJson(filePath);
        List<String> parcels = truckUnloadingService.unloadParcelsFromTrucks(trucks);

        log.info("Sending parcels to output...");
        outputService.SendParcelsToOutput(parcels);

        log.info("Unload trucks into parcels completed");
    }

    public ProgramMode selectProgramModeCommand(String command) {
        ProgramMode selectedMode;
        switch (command) {
            case "loading trucks" -> selectedMode = ProgramMode.LOADING_TRUCKS;
            case "unloading trucks" -> selectedMode = ProgramMode.UNLOADING_TRUCKS;
            default -> throw new IllegalStateException("Invalid program mode: " + command);
        }

        log.info("Selected program mode: {}", selectedMode);
        return selectedMode;
    }

    public LoadingMode selectLoadingModeCommand(String command) {
        LoadingMode selectedMode;
        switch (command) {
            case "loading to capacity" -> selectedMode = LoadingMode.LOADING_TO_CAPACITY;
            case "one by one" -> selectedMode = LoadingMode.ONE_BY_ONE;
            case "uniform" -> selectedMode = LoadingMode.UNIFORM;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        }

        log.info("Selected loading mode: {}", selectedMode);
        return selectedMode;
    }

    public Integer selectNumberOfTrucksCommand(String command) {
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

    public OutputType selectOutputTypeCommand(String command) {
        OutputType selectedOutputType;
        switch (command) {
            case "json" -> selectedOutputType = OutputType.JSON;
            case "console" -> selectedOutputType = OutputType.CONSOLE;
            default -> throw new IllegalStateException("Invalid output type: " + command);
        }

        log.info("Selected output type: {}", selectedOutputType);
        return selectedOutputType;
    }
}
