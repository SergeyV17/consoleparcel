package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.manager.OutputProcessorManager;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.OutputType;

import java.util.List;

@RequiredArgsConstructor
public class OutputService {

    private final OutputProcessorManager outputProcessorManager;

    public void SendTrucksToOutput(List<Truck> trucks, OutputType outputType) {
        outputProcessorManager.SendTrucksToOutput(trucks, outputType);
    }

    public void SendParcelsToOutput(List<String> parcels) {
        outputProcessorManager.SendParcelsToOutput(parcels, OutputType.TXT);
    }
}
