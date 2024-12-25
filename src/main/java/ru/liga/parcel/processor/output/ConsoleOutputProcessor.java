package ru.liga.parcel.processor.output;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.util.List;

@Slf4j
public class ConsoleOutputProcessor implements OutputProcessor {
    @Override
    public void writeTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            log.info(truck.toString());
        }
    }

    @Override
    public void writeParcels(List<String> parcels) {
        for (String parcel : parcels) {
            log.info(parcel);
        }
    }
}
