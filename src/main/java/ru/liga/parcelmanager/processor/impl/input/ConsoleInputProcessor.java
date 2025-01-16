package ru.liga.parcelmanager.processor.impl.input;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.liga.parcelmanager.command.CommandInvoker;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class ConsoleInputProcessor {

    private final CommandInvoker commandInvoker;

    @ShellMethod
    public void createParcel(
            @ShellOption String name,
            @ShellOption String form,
            @ShellOption String symbol) {
        commandInvoker.invoke("create -name " + name + " -form " + form + " -symbol " + symbol);
    }

    @ShellMethod
    public void deleteParcel(@ShellOption String name) {
        commandInvoker.invoke("delete -name " + name);
    }

    @ShellMethod
    public void findParcel(@ShellOption String name) {
        commandInvoker.invoke("find -name " + name);
    }

    @ShellMethod
    public void editParcel(
            @ShellOption String name,
            @ShellOption String form,
            @ShellOption String symbol) {
        commandInvoker.invoke("edit -name " + name + " -form " + form + " -symbol " + symbol);
    }

    @ShellMethod
    public void findAllParcels() {
        commandInvoker.invoke("find-all");
    }

    @ShellMethod
    public void loadParcel(
            @ShellOption String inputFile,
            @ShellOption String trucks,
            @ShellOption String type,
            @ShellOption String outputType,
            @ShellOption String outFileName) {
        commandInvoker.invoke(
                "load " +
                        "-file " + inputFile +
                        " -trucks " + trucks +
                        " -type " + type +
                        " -outputType " + outputType +
                        " -outFileName " + outFileName);
    }

    @ShellMethod
    public void unloadParcel(
            @ShellOption String inputFile,
            @ShellOption String outputFile,
            @ShellOption(defaultValue = "false") String withCount) {
        commandInvoker.invoke("unload" +
                "-input-file " + inputFile +
                " -output-file " + outputFile +
                " -with-count " + withCount);
    }
}
