package ru.liga;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.controller.ConsoleController;
import ru.liga.service.ParcelLoadingService;
import ru.liga.service.PrintingService;
import ru.liga.util.TxtParser;
import ru.liga.util.TxtReader;

@Slf4j
@SpringBootApplication
public class ParcelApplication {

    public static void main(String[] args) {
        log.info("App starting...");

        System.out.println("Program parcel loader. Version 1.0.0");
        System.out.println("How program works: ");
        System.out.println("1. Choose loading mode:");
        System.out.println("    - \"one by one\" - 1 truck 1 parcel");
        System.out.println("    - \"loading to capacity\" - load every truck to full capacity");
        System.out.println("2. Enter file path with parcels");
        System.out.println("3. Enter command \"exit\" or press \"Ctrl + C\" for exit from app.");

        ParcelApplication.start();
    }

    private static void start() {
        ConsoleController consoleController =
                new ConsoleController(
                        new ParcelLoadingService(new TxtParser(new TxtReader())),
                        new PrintingService());
        consoleController.listen();
    }
}
