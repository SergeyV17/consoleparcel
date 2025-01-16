package ru.liga.parcelmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.parcelmanager.command.*;
import ru.liga.parcelmanager.command.consts.CommandNames;
import ru.liga.parcelmanager.factory.ParcelFactory;
import ru.liga.parcelmanager.model.entity.Parcel;
import ru.liga.parcelmanager.processor.impl.input.TelegramInputProcessor;
import ru.liga.parcelmanager.properties.TelegramProperties;
import ru.liga.parcelmanager.repository.ParcelRepository;
import ru.liga.parcelmanager.service.ParcelService;
import ru.liga.parcelmanager.validation.ParcelValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public TelegramInputProcessor telegramInputProcessor(
            CommandInvoker commandInvoker,
            TelegramProperties telegramProperties) {
        return new TelegramInputProcessor(commandInvoker, telegramProperties);
    }

    @Bean
    public CommandInvoker commandInvoker() {
        return new CommandInvoker(
                commandRegistry(
                        createCommand(),
                        findCommand(),
                        findAllCommand(),
                        editCommand(),
                        deleteCommand(),
                        loadCommand(),
                        unloadCommand()));
    }

    @Bean
    public ParcelService parcelService() {
        return new ParcelService(parcelRepository());
    }

    @Bean
    public ParcelFactory parcelFactory() {
        return new ParcelFactory();
    }

    @Bean
    public ParcelValidator parcelValidator() {
        return new ParcelValidator();
    }

    @Bean
    public Command createCommand() {
        return new CreateCommand(parcelService(), parcelFactory(), parcelValidator());
    }

    @Bean
    public Command findCommand() {
        return new FindCommand();
    }

    @Bean
    public Command findAllCommand() {
        return new FindCommand();
    }

    @Bean
    public Command editCommand() {
        return new EditCommand();
    }

    @Bean
    public Command deleteCommand() {
        return new DeleteCommand(parcelService());
    }

    @Bean
    public Command loadCommand() {
        return new LoadCommand();
    }

    @Bean
    public Command unloadCommand() {
        return new LoadCommand();
    }

    @Bean
    public CommandRegistry commandRegistry(
            Command createCommand,
            Command findCommand,
            Command findAllCommand,
            Command editCommand,
            Command deleteCommand,
            Command loadCommand,
            Command unloadCommand) {
        return new CommandRegistry(Map.of(
                CommandNames.CREATE_COMMAND, createCommand,
                CommandNames.FIND_COMMAND, findCommand,
                CommandNames.FIND_ALL_COMMAND, findAllCommand,
                CommandNames.EDIT_COMMAND, editCommand,
                CommandNames.DELETE_COMMAND, deleteCommand,
                CommandNames.LOAD_COMMAND, loadCommand,
                CommandNames.UNLOAD_COMMAND, unloadCommand
        ));
    }

    @Bean
    public ParcelRepository parcelRepository() {

        List<Parcel> initialParcels = new ArrayList<>();

        String barbellForm = "#      #" +
                             "########" +
                             "#      #";

        String boxForm = "####" +
                         "####" +
                         "####";

        String swordForm = " # " +
                           " # " +
                           " # " +
                           "###" +
                           " # ";

        initialParcels.add(new Parcel("Barbell", barbellForm, "#"));
        initialParcels.add(new Parcel("Box", boxForm, "%"));
        initialParcels.add(new Parcel("Sword", swordForm, "@"));

        return new ParcelRepository(initialParcels);
    }
}
