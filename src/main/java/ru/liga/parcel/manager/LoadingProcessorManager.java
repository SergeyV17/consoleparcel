package ru.liga.parcel.manager;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.model.entity.Truck;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.processor.loading.FullCapacityLoadingProcessor;
import ru.liga.parcel.processor.loading.LoadingProcessor;
import ru.liga.parcel.processor.loading.OneByOneLoadingProcessor;
import ru.liga.parcel.processor.loading.UniformLoadingProcessor;
import ru.liga.parcel.validation.NumberOfTrucksValidator;

import java.util.List;

@RequiredArgsConstructor
public class LoadingProcessorManager {
    private final OneByOneLoadingProcessor oneByOneLoadingProcessor;
    private final FullCapacityLoadingProcessor fullCapacityLoadingProcessor;
    private final UniformLoadingProcessor uniformLoadingProcessor;

    private final NumberOfTrucksValidator numberOfTrucksValidator;

    public List<Truck> loadTrucks(List<String> cargo, LoadingMode mode, Integer numberOfTrucks) {
        if (numberOfTrucks != null) {
            numberOfTrucksValidator.validate(numberOfTrucks, cargo, mode);
        }
        LoadingProcessor loadingProcessor = getProcessorByLoadType(mode);
        return loadingProcessor.loadCargoIntoTrucks(cargo, numberOfTrucks);
    }

    private LoadingProcessor getProcessorByLoadType(LoadingMode mode) {
        switch (mode) {
            case LOADING_TO_CAPACITY -> {return fullCapacityLoadingProcessor;}
            case ONE_BY_ONE -> {return oneByOneLoadingProcessor;}
            case UNIFORM -> {return uniformLoadingProcessor;}
            default -> throw new IllegalArgumentException("Invalid loading mode: " + mode);
        }
    }
}
