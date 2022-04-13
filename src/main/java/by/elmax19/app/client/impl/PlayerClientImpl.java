package by.elmax19.app.client.impl;

import by.elmax19.app.client.PlayerClient;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.client.FeignClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerClientImpl implements PlayerClient {
    @Autowired
    private FeignClientUtils feignClient;

    public List<PlayerDto> getPlayersByClub(String clubName) {
        return feignClient.findPlayersByClub(clubName);
    }
}
