package by.elmax19.app.client.impl;

import by.elmax19.app.client.PlayerClient;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.UriParameterDto;
import by.elmax19.app.service.component.WebClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerClientImpl implements PlayerClient {
    @Autowired
    private WebClientUtils webClientUtils;
    @Value("${volleyball-players.api.base-url}")
    private String playersBaseUrl;
    @Value("${volleyball-players.api.players.path}")
    private String playersPath;
    @Value("${volleyball-players.api.players.club-parameter-name}")
    private String clubParameterName;

    public List<PlayerDto> getPlayersByClub(String clubName) {
        return webClientUtils.getListByParameters(
                playersBaseUrl,
                playersPath,
                List.of(new UriParameterDto(clubParameterName, clubName)),
                PlayerDto.class);
    }
}
