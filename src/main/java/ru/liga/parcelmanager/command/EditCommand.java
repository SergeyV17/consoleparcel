package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.command.consts.ArgumentsNames;
import ru.liga.parcelmanager.factory.ParcelFactory;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.service.ParcelService;
import ru.liga.parcelmanager.validation.ParcelValidator;

@RequiredArgsConstructor
public class EditCommand extends Command<Void> {

    private final ParcelService parcelService;
    private final ParcelFactory parcelFactory;
    private final ParcelValidator parcelValidator;

    @Override
    public Void execute(String[] args) {
        String name = getArgumentValue(args, ArgumentsNames.NAME);
        String form = getArgumentValue(args, ArgumentsNames.FORM);
        String symbol = getArgumentValue(args, ArgumentsNames.SYMBOL);

        Parcel parcel = parcelFactory.createParcel(name, form, symbol);
        parcelValidator.validateParcel(parcel);
        parcelService.editParcel(parcel);

        return null;
    }
}
