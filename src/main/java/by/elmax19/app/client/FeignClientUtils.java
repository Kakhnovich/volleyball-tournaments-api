package by.elmax19.app.client;

import by.elmax19.app.model.dto.PlayerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "FeignClientUtils", url = "http://localhost:8080")
public interface FeignClientUtils {
    @GetMapping("/players?club={clubName}")
    List<PlayerDto> findPlayersByClub(@PathVariable String clubName);
}
