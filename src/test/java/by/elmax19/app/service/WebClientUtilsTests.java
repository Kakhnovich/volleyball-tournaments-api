package by.elmax19.app.service;

import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.UriParameterDto;
import by.elmax19.app.service.component.WebClientUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebClientUtilsTests {
    private final String BASE_URL = "http://localhost:8080";
    private final String PLAYERS_PATH = "/players";
    private final String CLUB_PARAMETER_NAME = "club";
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    private WebClientUtils webClientUtils;

    @Test
    void checkResultOfRequest() {
        webClientUtils = new WebClientUtils(webClientMock);
        PlayerDto expectedPlayer = createPlayerDto();

        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(Function.class))).thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToFlux(PlayerDto.class)).thenReturn(Flux.just(expectedPlayer));

        List<PlayerDto> actualList = webClientUtils.getListByParameters(
                PLAYERS_PATH,
                List.of(new UriParameterDto(CLUB_PARAMETER_NAME, "Azimut Modena")),
                PlayerDto.class);

        assertEquals(1, actualList.size());
        assertEquals(expectedPlayer, actualList.get(0));

        verify(webClientMock).get();
        verify(requestHeadersUriMock).uri(any(Function.class));
        verify(requestHeadersMock).retrieve();
        verify(responseMock).bodyToFlux(PlayerDto.class);
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
