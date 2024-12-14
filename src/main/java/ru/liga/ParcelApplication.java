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
        log.info("Стартуем приложение...");

        System.out.println("Программа \"Парсер посылок\". Версия 1.0.0. ");
        System.out.println("Как работать с программой: ");
        System.out.println("1. Выберите алгоритм погрузки посылок");
        System.out.println("    - \"one by one\" - 1 грузовик 1 посылка");
        System.out.println("    - \"loading to capacity\" - погрузка посылок под завязку в один грузовик");
        System.out.println("2. Введите команду \"import\" и укажите путь к файлу, чтобы погрузить посылки.");
        System.out.println("3. Введите команду \"exit\" или Ctrl + C, для выхода из приложения.");

        ParcelApplication.start();
//        SpringApplication.run(ParcelApplication.class, args); TODO посмотреть надо ли стартовать через Spring
    }

    private static void start() {
        ConsoleController consoleController =
                new ConsoleController(
                        new ParcelLoadingService(new TxtParser(new TxtReader())),
                        new PrintingService());
        consoleController.listen();
    }
}
