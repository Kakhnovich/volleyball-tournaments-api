package by.elmax19.app.client;

import by.elmax19.app.model.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "FeignPlayerClient", url = "${volleyball-players.api.base-url}")
public interface FeignPlayerClient extends PlayerClient {
    @GetMapping("${volleyball-players.api.players.path}")
    List<PlayerDto> findPlayersByClub(@RequestParam String club);
}
