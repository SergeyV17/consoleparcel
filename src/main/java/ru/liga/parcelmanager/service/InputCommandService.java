package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.model.enums.ProgramMode;
import ru.liga.parcelmanager.util.JsonParser;
import ru.liga.parcelmanager.util.TxtParser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class InputCommandService {

    public static final String NUMBER_OF_TRUCKS_NOT_REQUIRED = "N";
    private final Pattern NUMBER_OF_TRUCKS_PATTERN = Pattern.compile("\\d+");

    private final CommandValidationService commandValidator;

    private final TxtParser txtParser;
    private final JsonParser jsonParser;
    private final ParcelLoadingService parcelLoadingService;
    private final TruckUnloadingService truckUnloadingService;
    private final OutputService outputService;

    public void loadTrucksCommand(String command, LoadingMode mode, Integer numberOfTrucks, OutputType outputType) {
        String filePath = commandValidator.validateLoadTruckCommandAndGetFilePath(command);

        log.info("Start loading parcels into trucks...");
        List<String> parcels = txtParser.parseCargoFromFile(filePath);

        List<Truck> parcelsWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(parcels, mode, numberOfTrucks);

        log.info("Sending trucks to output...");
        outputService.sendTrucksToOutput(parcelsWithinTrucks, outputType);

        log.info("Loading parcels into trucks completed");
    }

    public void unloadTrucksCommand(String command) {
        String filePath = commandValidator.validateUnloadTruckCommandAndGetFilePath(command);

        log.info("Start unloading trucks into parcels...");
        List<Truck> trucks = jsonParser.parseTrucksFromJson(filePath);
        List<String> parcels = truckUnloadingService.unloadParcelsFromTrucks(trucks);

        log.info("Sending parcels to output...");
        outputService.sendParcelsToOutput(parcels);

        log.info("Unload trucks into parcels completed");
    }

    public ProgramMode selectProgramModeCommand(String command) {
        return switch (command) {
            case "loading trucks" -> ProgramMode.LOADING_TRUCKS;
            case "unloading trucks" ->  ProgramMode.UNLOADING_TRUCKS;
            default -> throw new IllegalStateException("Invalid program mode: " + command);
        };
    }

    public LoadingMode selectLoadingModeCommand(String command) {
        return switch (command) {
            case "loading to capacity" -> LoadingMode.LOADING_TO_CAPACITY;
            case "one by one" ->  LoadingMode.ONE_BY_ONE;
            case "uniform" -> LoadingMode.UNIFORM;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        };
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

    public Integer selectNumberOfTrucksCommand(String command) {
        if (isNumberOfTruckRequired(command)) return null;

        Matcher matcher = NUMBER_OF_TRUCKS_PATTERN.matcher(command);
        if (matcher.matches()) {
            Integer numberOfTrucks = Integer.parseInt(command);
            log.info("Selected number of trucks: {}", numberOfTrucks);
            return numberOfTrucks;
        }

        throw new IllegalArgumentException("Invalid number of trucks: " + command);
    }

    private static boolean isNumberOfTruckRequired(String command) {
        return command.equals(NUMBER_OF_TRUCKS_NOT_REQUIRED);
    }
}
