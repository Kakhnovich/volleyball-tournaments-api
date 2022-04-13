package by.elmax19.app.service.impl;

import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.UriParameterDto;
import by.elmax19.app.service.PlayerService;
import by.elmax19.app.service.component.WebClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private WebClientUtils webClientUtils;
    @Value("${volleyball-players.api.base-url}")
    private String playersBaseUrl;
    @Value("${volleyball-players.api.players.path}")
    private String playersPath;
    @Value("${volleyball-players.api.players.club-parameter-name}")
    private String clubParameterName;

    @Override
    public List<PlayerDto> findPlayersByClub(String clubName) {
        return webClientUtils.getListByParameters(
                playersBaseUrl,
                playersPath,
                List.of(new UriParameterDto(clubParameterName, clubName)),
                PlayerDto.class);
    }
}
