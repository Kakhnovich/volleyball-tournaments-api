package by.elmax19.app.service.impl;

import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.UriParameterDto;
import by.elmax19.app.service.PlayerService;
import by.elmax19.app.service.component.WebClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final WebClientUtils webClientUtils;
    @Value("${volleyball-players.api.players.path}")
    private String playersPath;
    @Value("${volleyball-players.api.players.club-parameter-name}")
    private String clubParameterName;

    @Override
    public List<PlayerDto> findPlayersByClub(String clubName) {
        return webClientUtils.getListByParameters(
                playersPath, List.of(new UriParameterDto(clubParameterName, clubName)), PlayerDto.class);
    }
}
