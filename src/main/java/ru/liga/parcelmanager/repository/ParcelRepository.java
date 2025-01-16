package ru.liga.parcelmanager.repository;

import ru.liga.parcelmanager.exceptions.NotFoundException;
import ru.liga.parcelmanager.model.entity.Parcel;

import java.util.List;

public class ParcelRepository {

    private final List<Parcel> parcels;

    public ParcelRepository(List<Parcel> initialParcels) {
        parcels = initialParcels;
    }

    public void createParcel(Parcel parcel) {
        parcels.add(parcel);
    }

    public Parcel findParcel(String name) {
        Parcel parcel = parcels
                .stream()
                .filter(internalParcel -> internalParcel.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (parcel == null) {
            throw new NotFoundException("Parcel not found");
        }

        return parcel;
    }

    public List<Parcel> findAllParcels() {
        return parcels;
    }

    public void deleteParcel(String name) {
        boolean result = parcels.removeIf(parcel -> parcel.getName().equals(name));

        if (!result) {
            throw new NotFoundException("Parcel not found");
        }
    }

    public void editParcel(Parcel editedParcel) {
        Parcel parcel = findParcel(editedParcel.getName());
        if (parcel != null) {
            parcel.setForm(editedParcel.getForm());
            parcel.setSymbol(editedParcel.getSymbol());
        }

        throw new NotFoundException("Parcel not found");
    }
}
