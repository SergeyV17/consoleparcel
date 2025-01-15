package ru.liga.parcelmanager.validation;

import ru.liga.parcelmanager.exceptions.InvalidParcelFormException;

public class ParcelValidator {

    public void validateParcelForm(String form) {
        // TODO SERGEY VLASENKO реализовать валидацию формы посылки

        if (form.isEmpty()) {
            throw new InvalidParcelFormException("Parcel form cannot be empty");
        }
    }

    public void validateParcelName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Parcel name cannot be empty");
        }
    }

    public void validateParcelSymbol(String symbol) {
        if (symbol.isEmpty()) {
            throw new IllegalArgumentException("Parcel symbol cannot be empty");
        }
    }
}
