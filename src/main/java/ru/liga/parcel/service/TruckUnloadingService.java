package ru.liga.parcel.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.model.entity.Truck;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class TruckUnloadingService {
    public List<String> unloadParcelsFromTrucks(List<Truck> trucks) {
        Pattern digitGroupPattern = Pattern.compile("(\\d)\\1*");

        return trucks.stream()
                .flatMap(truck -> truck.getCargo().getParcels().stream())
                .flatMap(parcel -> {
                    Matcher matcher = digitGroupPattern.matcher(parcel);
                    List<String> splitParcels = matcher.results()
                            .map(MatchResult::group)
                            .toList();
                    return splitParcels.stream();
                })
                .toList();
    }
}
