package by.elmax19.app.controller;

import by.elmax19.app.exception.TournamentNotFoundException;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class TournamentController {
    private final TournamentService tournamentService;

    @GetMapping(UrlPatterns.TOURNAMENTS)
    public List<TournamentDto> findAllTournaments() {
        return tournamentService.findAll();
    }

    @GetMapping(UrlPatterns.SPECIFIC_TOURNAMENT)
    public TournamentDto findTournamentById(@PathVariable String tournamentId) {
        return tournamentService.findById(tournamentId);
    }

    @ExceptionHandler({TournamentNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    private String tournamentNotFoundException(RuntimeException e) {
        return e.getLocalizedMessage();
    }
}
