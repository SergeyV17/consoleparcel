package ru.liga.parcel.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.manager.OutputProcessorManager;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.OutputType;

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
