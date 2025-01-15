package ru.liga.parcelmanager.factory;

import ru.liga.parcelmanager.model.entity.Parcel;

public class ParcelFactory {

    public Parcel createParcel(String name, String form, String symbol) {
        Parcel parcel = new Parcel(name);
        parcel.setForm(form);
        parcel.setSymbol(symbol);
        return parcel;
    }
}
