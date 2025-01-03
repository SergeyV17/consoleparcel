package ru.liga.parcelmanager.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.parcelmanager.exceptions.InvalidTxtException;
import ru.liga.parcelmanager.model.enums.LoadingMode;
import ru.liga.parcelmanager.util.JsonParser;
import ru.liga.parcelmanager.util.TxtParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class CommandManagerTest {

    @Test
    public void testLoadTrucksCommand_validCommand_parsesCargoFromFile() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        LoadingProcessorService parcelLoadingService = Mockito.mock(LoadingProcessorService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        InputCommandService inputCommandService = new InputCommandService(
                new CommandValidationService(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService);
        String command = "path/to/file.txt";
        List<String> parcels = List.of("parcel1", "parcel2");
        Mockito.when(txtParser.parseParcelsFromFile(Mockito.any())).thenReturn(parcels);

        assertThatCode(() -> inputCommandService.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE, null)).doesNotThrowAnyException();
    }

    @Test
    public void testLoadTrucksCommand_invalidCommand_throwsIllegalArgumentException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        LoadingProcessorService parcelLoadingService = Mockito.mock(LoadingProcessorService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        InputCommandService inputCommandService = new InputCommandService(
                new CommandValidationService(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService);
        String command = "invalid command";

        assertThatThrownBy(() -> inputCommandService.loadTrucksCommand(command, LoadingMode.ONE_BY_ONE, null))
                .isInstanceOf(InvalidTxtException.class)
                .hasMessageContaining("invalid command");
    }

    @Test
    public void testSelectModeCommand_validCommand_returnsLoadingMode() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        LoadingProcessorService parcelLoadingService = Mockito.mock(LoadingProcessorService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        InputCommandService inputCommandService = new InputCommandService(
                new CommandValidationService(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService);
        String command = "loading to capacity";

        LoadingMode mode = inputCommandService.selectLoadingModeCommand(command);

        assertThat(mode).isEqualTo(LoadingMode.LOADING_TO_CAPACITY);
    }

    @Test
    public void testSelectModeCommand_invalidCommand_throwsIllegalStateException() {
        TxtParser txtParser = Mockito.mock(TxtParser.class);
        JsonParser jsonParser = Mockito.mock(JsonParser.class);
        LoadingProcessorService parcelLoadingService = Mockito.mock(LoadingProcessorService.class);
        TruckUnloadingService truckUnloadingService = Mockito.mock(TruckUnloadingService.class);
        InputCommandService inputCommandService = new InputCommandService(
                new CommandValidationService(),
                txtParser,
                jsonParser,
                parcelLoadingService,
                truckUnloadingService);
        String command = "invalid command";

        assertThatThrownBy(() -> inputCommandService.selectLoadingModeCommand(command))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("invalid command");
    }
}