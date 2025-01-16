package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.service.ParcelService;

import java.util.List;

@RequiredArgsConstructor
public class FindAllCommand extends Command<List<Parcel>> {

    private final ParcelService parcelService;

    @Override
    public List<Parcel> execute(String[] args) {
        return parcelService.findAllParcels();
    }
}
