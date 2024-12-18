package ru.liga.parcel.service;

import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.entity.Truck;

import java.util.List;

@Slf4j
public class PrintingService {
    public void PrintTrucks(List<Truck> trucks) {
        for (Truck truck : trucks) {
            log.info(truck.toString());
        }
    }
}
