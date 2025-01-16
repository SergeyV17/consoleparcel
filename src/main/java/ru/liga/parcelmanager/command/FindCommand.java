package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.command.consts.ArgumentsNames;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.service.ParcelService;

@RequiredArgsConstructor
public class FindCommand extends Command<Parcel> {

    private final ParcelService parcelService;

    @Override
    public Parcel execute(String[] args) {
        return parcelService.findParcel(getArgumentValue(args, ArgumentsNames.NAME));
    }
}
