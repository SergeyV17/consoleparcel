package ru.liga.parcelmanager.service;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.model.entity.Truck;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.processor.impl.FullCapacityLoadingProcessor;
import ru.liga.parcelmanager.processor.LoadingProcessor;
import ru.liga.parcelmanager.processor.impl.OneByOneLoadingProcessor;
import ru.liga.parcelmanager.processor.impl.UniformLoadingProcessor;

import java.util.List;

@RequiredArgsConstructor
public class LoadingProcessorService {

    private final OneByOneLoadingProcessor oneByOneLoadingProcessor;
    private final FullCapacityLoadingProcessor fullCapacityLoadingProcessor;
    private final UniformLoadingProcessor uniformLoadingProcessor;

    private final TruckValidationService numberOfTrucksValidator;

    public List<Truck> loadTrucks(List<String> parcels, LoadingMode mode, Integer numberOfTrucks) {
        if (numberOfTrucks != null) {
            numberOfTrucksValidator.validateNumberOfTrucks(numberOfTrucks, parcels, mode);
        }
        LoadingProcessor loadingProcessor = getProcessorByLoadType(mode);
        return loadingProcessor.loadParcelsIntoTrucks(parcels, numberOfTrucks);
    }

    private LoadingProcessor getProcessorByLoadType(LoadingMode mode) {
        return switch (mode) {
            case LOADING_TO_CAPACITY -> fullCapacityLoadingProcessor;
            case ONE_BY_ONE -> oneByOneLoadingProcessor;
            case UNIFORM -> uniformLoadingProcessor;
            default -> throw new IllegalArgumentException("Invalid loading mode: " + mode);
        };
    }
}
