package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.repository.ParcelRepository;

import java.util.List;

@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public void createParcel(Parcel parcel) {
        parcelRepository.createParcel(parcel);
    }

    public Parcel findParcel(String name) {
        return parcelRepository.findParcel(name);
    }

    public List<Parcel> findAllParcels() {
        return parcelRepository.findAllParcels();
    }

    public void editParcel(Parcel editedParcel) {
        parcelRepository.editParcel(editedParcel);
    }

    public void deleteParcel(String name) {
        parcelRepository.deleteParcel(name);
    }
}
