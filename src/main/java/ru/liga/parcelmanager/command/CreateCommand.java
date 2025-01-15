package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.factory.ParcelFactory;
import ru.liga.parcelmanager.service.ParcelService;
import ru.liga.parcelmanager.validation.ParcelValidator;

@RequiredArgsConstructor
public class CreateCommand extends Command {

    private final ParcelService parcelService;
    private final ParcelFactory parcelFactory;
    private final ParcelValidator parcelValidator;

    @Override
    public void execute(String[] args) {
        String name = getOptionalValue(args, "-name");
        String form = getOptionalValue(args, "-form");
        String symbol = getOptionalValue(args, "-symbol");

        validateParcel(name, form, symbol);
        parcelService.createParcel(parcelFactory.createParcel(name, form, symbol));
    }

    private void validateParcel(String name, String form, String symbol) {
        parcelValidator.validateParcelName(name);
        parcelValidator.validateParcelForm(form);
        parcelValidator.validateParcelSymbol(symbol);
    }
}
