package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.processor.impl.ConsoleOutputProcessor;
import ru.liga.parcelmanager.processor.impl.JsonOutputProcessor;
import ru.liga.parcelmanager.processor.OutputProcessor;
import ru.liga.parcelmanager.processor.impl.TxtOutputProcessor;

import java.util.List;

@RequiredArgsConstructor
public class OutputProcessorService {

    private final ConsoleOutputProcessor consoleOutputProcessor;
    private final JsonOutputProcessor jsonOutputProcessor;
    private final TxtOutputProcessor txtOutputProcessor;

    public void sendTrucksToOutput(List<Truck> trucks, OutputType outputType) {
        OutputProcessor outputProcessor = getOutputServiceByOutputType(outputType);
        outputProcessor.writeTrucks(trucks);
    }

    public void sendParcelsToOutput(List<String> parcels, OutputType outputType) {
        OutputProcessor outputProcessor = getOutputServiceByOutputType(outputType);
        outputProcessor.writeParcels(parcels);
    }

    private OutputProcessor getOutputServiceByOutputType(OutputType outputType) {
        switch (outputType) {
            case CONSOLE -> {return consoleOutputProcessor;}
            case JSON -> {return jsonOutputProcessor;}
            case TXT -> {return txtOutputProcessor;}
            default -> throw new IllegalArgumentException("Invalid output type: " + outputType);
        }
    }
}
