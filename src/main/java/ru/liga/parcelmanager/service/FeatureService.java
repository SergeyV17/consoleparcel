package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.model.enums.ProgramMode;

import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
public class FeatureService {

    private final Scanner scanner;
    private final InputCommandService inputCommandService;

    public void selectFeatureScenario() {
        log.info("Select program mode: \"loading trucks\", \"unloading trucks\"");
        String programModeLine = scanner.nextLine();
        ProgramMode programMode = inputCommandService.selectProgramModeCommand(programModeLine);
        log.info("Selected program mode: {}", programMode);

        switch (programMode) {
            case LOADING_TRUCKS:
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
                inputCommandService.loadTrucksCommand(inputFilePath, loadingMode, numberOfTrucks, outputType);
                break;

            case UNLOADING_TRUCKS:
                log.info("Enter input json file path: ");
                String jsonFileLine = scanner.nextLine();
                inputCommandService.unloadTrucksCommand(jsonFileLine);
                break;
        }
    }
}
