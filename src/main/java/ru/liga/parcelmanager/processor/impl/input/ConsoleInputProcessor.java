package ru.liga.parcelmanager.processor.impl.input;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcelmanager.command.CommandInvoker;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.model.enums.ProgramMode;
import ru.liga.parcelmanager.processor.InputProcessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ConsoleInputProcessor implements InputProcessor {

    private final CommandInvoker commandInvoker;

    @Override
    public void listen() {
        do {
            try {
                log.info("Select program mode: \"loading trucks\", \"unloading trucks\" or press \"exit\"");
                String command = scanner.nextLine();

                if (inputCommandService.isExitCommand(command)) {
                    log.info("Goodbye!");
                    return;
                }

                if (inputCommandService.isSelectProgramModeCommand(command)) {
                    ProgramMode programMode = inputCommandService.selectProgramModeCommand(command);
                    log.info("Selected program mode: {}", programMode);

                    Output<?> output = switch (programMode) {
                        case LOADING_TRUCKS -> loadTrucks();
                        case UNLOADING_TRUCKS -> unloadTrucks();
                    };
                    outputService.sendValuesToOutput(output);
                    log.info("Output completed");
                    continue;
                }

                log.error("Invalid command: {}. Try again or press \"exit\"", command);
            }
            catch (Exception e) {
                log.error("An exception occurred {}. Please try again", e.getMessage());
            }
        }
        while(true);
    }

    private Output<String> unloadTrucks() {
        log.info("Enter input json file path: ");
        String jsonFileLine = scanner.nextLine();
        return new Output<>(TRUCKS_FILE_NAME, OutputType.TXT, inputCommandService.unloadTrucksCommand(jsonFileLine));
    }

    private Output<Truck> loadTrucks() {
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
        List<Truck> trucks = inputCommandService.loadTrucksCommand(
                inputFilePath,
                loadingMode,
                numberOfTrucks);

        return new Output<>(TRUCKS_FILE_NAME, outputType, trucks);
    }
}
