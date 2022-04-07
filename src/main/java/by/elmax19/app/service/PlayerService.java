package by.elmax19.app.service;

import by.elmax19.app.model.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> findPlayersByClub(String clubName);
}
