package ru.liga.parcelmanager.command;

import lombok.RequiredArgsConstructor;
import ru.liga.parcelmanager.command.consts.ArgumentsNames;
import ru.liga.parcelmanager.service.ParcelService;

@RequiredArgsConstructor
public class DeleteCommand extends Command<Void> {

    private final ParcelService parcelService;

    @Override
    public Void execute(String[] args) {
        String name = getArgumentValue(args, ArgumentsNames.NAME);
        parcelService.deleteParcel(name);

        return null;
    }
}
