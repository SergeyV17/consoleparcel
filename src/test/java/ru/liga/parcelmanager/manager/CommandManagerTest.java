package ru.liga.parcelmanager.manager;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.model.enums.OutputType;
import ru.liga.parcelmanager.service.OutputService;
import ru.liga.parcelmanager.service.ParcelLoadingService;
import ru.liga.parcelmanager.service.TruckUnloadingService;
import ru.liga.parcelmanager.util.JsonParser;
import ru.liga.parcelmanager.util.TxtParser;
import ru.liga.parcelmanager.validation.CommandValidator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class CommandManagerTest {
    @Test
    public void testImportCommand_validCommand_parsesCargoFromFile() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        OutputService outputService = Mockito.mock(OutputService.class);
        CommandManager commandManager = new CommandManager(
                new CommandValidator(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService,
                outputService);
        String command = "path/to/file.txt";
        List<String> parcels = List.of("parcel1", "parcel2");
        Mockito.when(txtParser.parseCargoFromFile(Mockito.any())).thenReturn(parcels);

        assertThatCode(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE, null, OutputType.CONSOLE)).doesNotThrowAnyException();
    }

    @Test
    public void testImportCommand_invalidCommand_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        OutputService outputService = Mockito.mock(OutputService.class);
        CommandManager commandManager = new CommandManager(
                new CommandValidator(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService,
                outputService);
        String command = "invalid command";

        assertThatThrownBy(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE, null, OutputType.CONSOLE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid command");
    }

    @Test
    public void testImportCommand_emptyParcels_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        OutputService outputService = Mockito.mock(OutputService.class);
        CommandManager commandManager = new CommandManager(
                new CommandValidator(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService,
                outputService);
        String command = "path/to/file.txt";
        Mockito.when(txtParser.parseCargoFromFile(Mockito.any())).thenReturn(List.of());

        assertThatThrownBy(() -> commandManager.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE, null, OutputType.CONSOLE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Parcels not found in path/to/file.txt");
    }

    @Test
    public void testSelectModeCommand_validCommand_returnsLoadingMode() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        OutputService outputService = Mockito.mock(OutputService.class);
        CommandManager commandManager = new CommandManager(
                new CommandValidator(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService,
                outputService);
        String command = "loading to capacity";

        LoadingMode mode = commandManager.selectLoadingModeCommand(command);

        assertThat(mode).isEqualTo(LoadingMode.LOADING_TO_CAPACITY);
    }

    @Test
    public void testSelectModeCommand_invalidCommand_throwsIllegalStateException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        ParcelLoadingService parcelLoadingService = Mockito.mock(ParcelLoadingService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        OutputService outputService = Mockito.mock(OutputService.class);
        CommandManager commandManager = new CommandManager(
                new CommandValidator(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService,
                outputService);
        String command = "invalid command";

        assertThatThrownBy(() -> commandManager.selectLoadingModeCommand(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("invalid command");
    }
}