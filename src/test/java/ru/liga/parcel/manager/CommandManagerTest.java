package ru.liga.parcel.manager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcel.model.enums.LoadingMode;
import ru.liga.parcel.service.ParcelLoadingService;
import ru.liga.parcel.processor.output.ConsoleOutputProcessor;
import ru.liga.parcel.util.TxtParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class CommandManagerTest {
    @Test
    public void testImportCommand_validCommand_parsesCargoFromFile() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        ConsoleOutputProcessor printingService = Mockito.mock(ConsoleOutputProcessor.class);
        CommandManager commandManager = new CommandManager(txtParser, parcelLoadingService, printingService);
        String command = "path/to/file.txt";
        List<String> parcels = List.of("parcel1", "parcel2");
        Mockito.when(txtParser.parseCargoFromFile(Mockito.any())).thenReturn(parcels);

        commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE);

        assertThatCode(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE)).doesNotThrowAnyException();
    }

    @Test
    public void testImportCommand_invalidCommand_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        ConsoleOutputProcessor printingService = Mockito.mock(ConsoleOutputProcessor.class);
        CommandManager commandManager = new CommandManager(txtParser, parcelLoadingService, printingService);
        String command = "invalid command";

        assertThatThrownBy(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid command: invalid command");
    }

    @Test
    public void testImportCommand_emptyParcels_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        ConsoleOutputProcessor printingService = Mockito.mock(ConsoleOutputProcessor.class);
        CommandManager commandManager = new CommandManager(txtParser, parcelLoadingService, printingService);
        String command = "path/to/file.txt";
        Mockito.when(txtParser.parseCargoFromFile(Mockito.any())).thenReturn(List.of());

        assertThatThrownBy(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parcels not found in path/to/file.txt");
    }

    @Test
    public void testSelectModeCommand_validCommand_returnsLoadingMode() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        ConsoleOutputProcessor printingService = Mockito.mock(ConsoleOutputProcessor.class);
        CommandManager commandManager = new CommandManager(txtParser, parcelLoadingService, printingService);
        String command = "loading to capacity";

        LoadingMode mode = commandManager.selectLoadingModeCommand(command);

        assertThat(mode).isEqualTo(LoadingMode.LOADING_TO_CAPACITY);
    }

    @Test
    public void testSelectModeCommand_invalidCommand_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        ConsoleOutputProcessor printingService = Mockito.mock(ConsoleOutputProcessor.class);
        CommandManager commandManager = new CommandManager(txtParser, parcelLoadingService, printingService);
        String command = "invalid command";

        assertThatThrownBy(() -> commandManager.selectLoadingModeCommand(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid command: invalid command");
    }
}