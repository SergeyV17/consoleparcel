package ru.liga.parcelmanager.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.service.FeatureService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final FeatureService featureManager;

    public void start() {
        log.info(
        """
        Program parcel loader. Version 1.0.0
        How program works:
        1. Choose program mode:
            - "loading trucks" - load parcels into trucks
            - "unloading trucks" - unload parcels from trucks

        2. Program mode behaviors:
            Loading trucks:
            1. Choose loading mode:
                - "one by one" - 1 truck 1 parcel
                - "loading to capacity" - load every truck to full capacity
                - "uniform" - uniform loading to every truck
            2. Choose number of trucks or press "N" for default behavior
            3. Choose output type:
                - "console" - print to console
                - "json" - write trucks structure to json file
            4. Enter file path with parcels

            Unloading trucks:
            1. Enter json file path with trucks
        3. Press "Ctrl + C" for exit from app.
        """);

        featureManager.selectFeatureScenario();
    }
}
