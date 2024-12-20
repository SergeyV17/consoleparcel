package ru.liga.parcel.factory;

import ru.liga.parcel.model.entity.Cargo;
import ru.liga.parcel.model.entity.Truck;

public class TruckFactory {
    public Truck createTruck(Cargo cargo) {
        Truck truck = new Truck();
        truck.loadTruck(cargo);
        return truck;
    }
}
