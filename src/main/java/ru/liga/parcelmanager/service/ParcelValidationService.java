package ru.liga.parcelmanager.service;

import ru.liga.parcelmanager.exceptions.InvalidParcelFormException;

public class ParcelValidationService {

    public void validateParcelForm(String form) {
        // TODO SERGEY VLASENKO реализовать валидацию формы посылки

        if (form.isEmpty()) {
            throw new InvalidParcelFormException("Parcel form cannot be empty");
        }
    }
}
