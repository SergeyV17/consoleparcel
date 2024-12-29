package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Truck;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TruckUnloadingService {

    public static final String DIGIT_GROUP_PATTERN = "(\\d)\\1*";

    public List<String> unloadParcelsFromTrucks(List<Truck> trucks) {
        Pattern digitGroupPattern = Pattern.compile(DIGIT_GROUP_PATTERN);

        return trucks.stream()
                .flatMap(truck -> truck.getCargo().getParcels().stream())
                .flatMap(parcel -> createSplitParcels(parcel, digitGroupPattern))
                .toList();
    }

    private Stream<String> createSplitParcels(String parcel, Pattern digitGroupPattern) {
        Matcher matcher = digitGroupPattern.matcher(parcel);
        List<String> splitParcels = matcher.results()
                .map(MatchResult::group)
                .toList();
        return splitParcels.stream();
    }
}
