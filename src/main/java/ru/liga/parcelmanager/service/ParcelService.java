package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.repository.ParcelRepository;

@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public void createParcel(Parcel parcel) {
        parcelRepository.createParcel(parcel);
    }
}
