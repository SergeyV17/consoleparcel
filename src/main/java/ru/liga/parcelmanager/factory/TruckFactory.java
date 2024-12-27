package ru.liga.parcelmanager.factory;

import ru.liga.parcelmanager.model.entity.Cargo;
import ru.liga.parcelmanager.model.entity.Truck;

public class TruckFactory {

    public Truck createTruck(Cargo cargo) {
        Truck truck = new Truck();
        truck.loadTruck(cargo);
        return truck;
    }
}
