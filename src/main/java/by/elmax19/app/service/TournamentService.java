package by.elmax19.app.service;

import by.elmax19.app.model.dto.TournamentDto;

import java.util.List;

public interface TournamentService {
    List<TournamentDto> findAll();

    TournamentDto findById(String id);
}
