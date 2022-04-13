package by.elmax19.app.service;

import by.elmax19.app.client.PlayerClient;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.service.impl.PlayerServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTests {
    @Mock
    private PlayerClient playerClient;
    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void checkFindingPlayersList() {
        String clubName = "Azimut Modena";
        PlayerDto expectedPlayer = createPlayerDto();

        when(playerClient.getPlayersByClub(clubName)).thenReturn(List.of(expectedPlayer));

        List<PlayerDto> actualPlayers = playerService.findPlayersByClub(clubName);

        assertEquals(1, actualPlayers.size());
        assertEquals(expectedPlayer, actualPlayers.get(0));
    }

    private PlayerDto createPlayerDto() {
        return PlayerDto.builder()
                .id(new ObjectId().toString())
                .fullName("Nimir Abdel-Aziz")
                .age(30)
                .height(2.01)
                .spike(360)
                .block(340)
                .position("OPPOSITE_HITTER")
                .club("Azimut Modena")
                .number(14)
                .build();
    }
}
