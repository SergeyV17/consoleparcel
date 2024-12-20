package ru.liga.parcel.manager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.service.PrintingService;
import ru.liga.parcel.util.TxtParser;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommandManagerTest {
    @Test
    void importCommand_validCommand_parsesCargoFromFile() {
        String command = "path/to/file.txt";
        var txtParser = Mockito.mock(TxtParser.class);
        var commandManager = new CommandManager(
                txtParser,
                Mockito.mock(ParcelLoadingService.class),
                Mockito.mock(PrintingService.class));

        when(txtParser.parseCargoFromFile(any())).thenReturn(List.of("parcel1", "parcel2"));

        commandManager.importCommand(command, LoadingMode.ONE_BY_ONE);

        assertThatCode(() -> commandManager.importCommand(command, LoadingMode.ONE_BY_ONE)).doesNotThrowAnyException();
    }

    @Test
    void importCommand_invalidCommand_throwsIllegalArgumentException() {
        String command = "invalid command";

        var commandManager = new CommandManager(
                Mockito.mock(TxtParser.class),
                Mockito.mock(ParcelLoadingService.class),
                Mockito.mock(PrintingService.class));

        assertThatThrownBy(() -> commandManager.importCommand(command, LoadingMode.ONE_BY_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid command: invalid command");
    }

    @Test
    void importCommand_emptyParcels_throwsIllegalArgumentException() {
        String command = "path/to/file.txt";

        var txtParser = Mockito.mock(TxtParser.class);
        var commandManager = new CommandManager(
                txtParser,
                Mockito.mock(ParcelLoadingService.class),
                Mockito.mock(PrintingService.class));

        when(txtParser.parseCargoFromFile(any())).thenReturn(List.of());

        assertThatThrownBy(() -> commandManager.importCommand(command, LoadingMode.ONE_BY_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parcels not found in path/to/file.txt");
    }

    @Test
    void selectModeCommand_validCommand_returnsLoadingMode() {
        String command = "loading to capacity";
        var commandManager = new CommandManager(
                Mockito.mock(TxtParser.class),
                Mockito.mock(ParcelLoadingService.class),
                Mockito.mock(PrintingService.class));

        LoadingMode mode = commandManager.selectModeCommand(command);

        assertThat(mode).isEqualTo(LoadingMode.LOADING_TO_CAPACITY);
    }

    @Test
    void selectModeCommand_invalidCommand_throwsIllegalArgumentException() {
        String command = "invalid command";
        var commandManager = new CommandManager(
                Mockito.mock(TxtParser.class),
                Mockito.mock(ParcelLoadingService.class),
                Mockito.mock(PrintingService.class));

        assertThatThrownBy(() -> commandManager.selectModeCommand(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid command: invalid command");
    }
}