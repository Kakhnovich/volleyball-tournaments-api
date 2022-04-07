package by.elmax19.app.service.impl;

import by.elmax19.app.exception.TournamentNotFoundException;
import by.elmax19.app.mapper.TournamentMapper;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.repository.TournamentRepository;
import by.elmax19.app.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;

    @Override
    public List<TournamentDto> findAll() {
        return tournamentMapper.convertListToDto(tournamentRepository.findAll());
    }

    @Override
    public TournamentDto findById(String id) {
        return tournamentRepository.findById(new ObjectId(id))
                .map(tournamentMapper::convertToDto)
                .orElseThrow(() -> new TournamentNotFoundException("There isn't tournament with id: " + id));
    }
}
