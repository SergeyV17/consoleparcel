package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.Output;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.processor.impl.output.ConsoleOutputProcessor;
import ru.liga.parcelmanager.processor.impl.output.JsonOutputProcessor;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.processor.impl.output.TxtOutputProcessor;

@RequiredArgsConstructor
public class OutputService {

    private final ConsoleOutputProcessor consoleOutputProcessor;
    private final JsonOutputProcessor jsonOutputProcessor;
    private final TxtOutputProcessor txtOutputProcessor;

    public void sendValuesToOutput(Output<?> output) {
        OutputProcessor outputProcessor = getOutputServiceByOutputType(output.getOutputType());
        outputProcessor.write(output);
    }

    private OutputProcessor getOutputServiceByOutputType(OutputType outputType) {
        return switch (outputType) {
            case CONSOLE ->  consoleOutputProcessor;
            case JSON -> jsonOutputProcessor;
            case TXT -> txtOutputProcessor;
        };
    }
}
