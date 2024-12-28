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
    public static final String JSON_OUTOUT_TYPE = "json";
    public static final String CONSOLE_OUTPUT_TYPE = "console";
    public static final String LOADING_TRUCKS_COMMAND = "loading trucks";
    public static final String UNLOADING_TRUCKS_COMMAND = "unloading trucks";
    public static final String LOADING_TO_CAPACITY_MODE = "loading to capacity";
    public static final String ONE_BY_ONE_MODE = "one by one";
    public static final String UNIFORM_MODE = "uniform";
    private final Pattern NUMBER_OF_TRUCKS_PATTERN = Pattern.compile("\\d+");

    private final CommandValidationService commandValidator;

    private final TxtParser txtParser;
    private final JsonParser jsonParser;
    private final ParcelLoadingService parcelLoadingService;
    private final TruckUnloadingService truckUnloadingService;

    public List<Truck> loadTrucksCommand(String command, LoadingMode mode, Integer numberOfTrucks) {
        String filePath = commandValidator.validateLoadTruckCommandAndGetFilePath(command);

        log.info("Start loading parcels into trucks...");
        List<String> parcels = txtParser.parseParcelsFromFile(filePath);

        List<Truck> trucks = parcelLoadingService.loadParcelsIntoTrucks(parcels, mode, numberOfTrucks);
        log.info("Loading parcels into trucks completed");

        return trucks;
    }

    public List<String> unloadTrucksCommand(String command) {
        String filePath = commandValidator.validateUnloadTruckCommandAndGetFilePath(command);

        log.info("Start unloading trucks into parcels...");
        List<Truck> trucks = jsonParser.parseTrucksFromJson(filePath);
        return truckUnloadingService.unloadParcelsFromTrucks(trucks);
    }

    public ProgramMode selectProgramModeCommand(String command) {
        return switch (command) {
            case LOADING_TRUCKS_COMMAND -> ProgramMode.LOADING_TRUCKS;
            case UNLOADING_TRUCKS_COMMAND ->  ProgramMode.UNLOADING_TRUCKS;
            default -> throw new IllegalStateException("Invalid program mode: " + command);
        };
    }

    public LoadingMode selectLoadingModeCommand(String command) {
        return switch (command) {
            case LOADING_TO_CAPACITY_MODE -> LoadingMode.LOADING_TO_CAPACITY;
            case ONE_BY_ONE_MODE ->  LoadingMode.ONE_BY_ONE;
            case UNIFORM_MODE -> LoadingMode.UNIFORM;
            default -> throw new IllegalStateException("Invalid loading mode: " + command);
        };
    }

    public OutputType selectOutputTypeCommand(String command) {
        OutputType selectedOutputType;
        switch (command) {
            case JSON_OUTOUT_TYPE -> selectedOutputType = OutputType.JSON;
            case CONSOLE_OUTPUT_TYPE -> selectedOutputType = OutputType.CONSOLE;
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

    private boolean isNumberOfTruckRequired(String command) {
        return command.equals(NUMBER_OF_TRUCKS_NOT_REQUIRED);
    }

    public boolean isExitCommand(String command) {
        return command.equals("exit");
    }

    public boolean isSelectProgramModeCommand(String command) {
        return command.equals("loading trucks") || command.equals("unloading trucks");
    }
}
