package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.processor.FullCapacityLoadingProcessor;
import ru.liga.parcel.processor.LoadingProcessor;
import ru.liga.parcel.processor.OneByOneLoadingProcessor;

import java.util.List;

@RequiredArgsConstructor
public class LoadingProcessorManager {
    private final FullCapacityLoadingProcessor fullCapacityLoadingProcessor;
    private final OneByOneLoadingProcessor oneByOneLoadingProcessor;

    public List<Truck> loadTrucks(List<String> cargo, LoadingMode mode) {
        LoadingProcessor loadingProcessor = getProcessorByLoadType(mode);
        return loadingProcessor.loadCargoIntoTrucks(cargo);
    }

    private LoadingProcessor getProcessorByLoadType(LoadingMode mode) {
        switch (mode) {
            case LOADING_TO_CAPACITY -> {return fullCapacityLoadingProcessor;}
            case ONE_BY_ONE -> {return oneByOneLoadingProcessor;}
            default -> throw new IllegalArgumentException("Invalid loading mode: " + mode);
        }
    }
}
