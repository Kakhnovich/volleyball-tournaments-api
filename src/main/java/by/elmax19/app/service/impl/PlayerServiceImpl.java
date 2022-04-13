package by.elmax19.app.service.impl;

import by.elmax19.app.client.PlayerClient;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerClient playerClient;

    @Override
    public List<PlayerDto> findPlayersByClub(String clubName) {
        return playerClient.getPlayersByClub(clubName);
    }
}
