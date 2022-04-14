package by.elmax19.app.client;

import by.elmax19.app.model.dto.PlayerDto;

import java.util.List;

public interface PlayerClient {
    List<PlayerDto> findPlayersByClub(String clubName);
}
