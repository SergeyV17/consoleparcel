package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.OutputType;

import java.util.List;

@RequiredArgsConstructor
public class OutputService {

    private final OutputProcessorService outputProcessorManager;

    public void sendTrucksToOutput(List<Truck> trucks, OutputType outputType) {
        outputProcessorManager.sendTrucksToOutput(trucks, outputType);
    }

    public void sendParcelsToOutput(List<String> parcels) {
        outputProcessorManager.sendParcelsToOutput(parcels, OutputType.TXT);
    }
}
