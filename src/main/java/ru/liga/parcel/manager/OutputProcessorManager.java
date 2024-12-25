package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.OutputType;
import ru.liga.parcel.processor.output.ConsoleOutputProcessor;
import ru.liga.parcel.processor.output.JsonOutputProcessor;
import ru.liga.parcel.processor.output.OutputProcessor;
import ru.liga.parcel.processor.output.TxtOutputProcessor;

import java.util.List;

@RequiredArgsConstructor
public class OutputProcessorManager {
    private final ConsoleOutputProcessor consoleOutputProcessor;
    private final JsonOutputProcessor jsonOutputProcessor;
    private final TxtOutputProcessor txtOutputProcessor;

    public void SendTrucksToOutput(List<Truck> trucks, OutputType outputType) {
        OutputProcessor outputProcessor = getOutputServiceByOutputType(outputType);
        outputProcessor.writeTrucks(trucks);
    }

    public void SendParcelsToOutput(List<String> parcels, OutputType outputType) {
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
