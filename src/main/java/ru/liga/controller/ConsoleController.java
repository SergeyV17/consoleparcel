package ru.liga.controller;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ru.liga.model.enums.LoadingMode;
import ru.liga.service.ParcelLoadingService;
import ru.liga.service.PrintingService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final ParcelLoadingService parcelLoadingService;
    private final PrintingService printingService;

    private final Pattern IMPORT_COMMAND_PATTERN = Pattern.compile("(.+\\.txt)");
    private final Pattern LOADING_MODE_PATTERN = Pattern.compile("(loading to capacity|one by one)");

    public void listen() {
        var scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            var command = scanner.nextLine();
            if (command.equals("exit")) {
                System.exit(0);
            }

            // TODO подумать как лучше вынести
            LoadingMode mode = LoadingMode.NONE;
            if (LOADING_MODE_PATTERN.matcher(command).matches()) {
                switch (command) {
                    case "loading to capacity" -> mode = LoadingMode.LOADING_TO_CAPACITY;
                    case "one by one" -> mode = LoadingMode.ONE_BY_ONE;
                    default -> throw new IllegalStateException("Невалидный режим погрузки: " + command);
                }

                log.info("Выбран режим погрузки: " + mode);
            }

            if (mode == LoadingMode.NONE) {
                System.out.println("Сначала выберите режим погрузки: " + command);
                log.error("Пользователь не выбрал режим погрузки: " + command);
                continue;
            }

            // TODO подумать как лучше вынести
            Matcher importCommandMatcher = IMPORT_COMMAND_PATTERN.matcher(command);
            if (importCommandMatcher.matches()) {
                String filePath = importCommandMatcher.group(1);

                log.info("Старт погрузки посылок в грузовики...");
                var cargosWithinTrucks = parcelLoadingService.loadParcelsIntoTrucks(filePath, mode);
                if (cargosWithinTrucks.isEmpty()) {
                    System.out.println("Не найдено валидного груза. Повторите попытку с другим файлом");
                    log.error("В файле {} не обнаружено валидного груза.", filePath);
                    continue;
                }

                log.info("Печать грузовиков в консоль...");
                printingService.PrintTrucks(cargosWithinTrucks);

                log.info("Погрузка посылок в грузовики завершена.");
            }

            System.out.println("Некорректная команда: " + command);
            log.error("Пользователь ввел некорректную команду: " + command);
        }
    }

}
