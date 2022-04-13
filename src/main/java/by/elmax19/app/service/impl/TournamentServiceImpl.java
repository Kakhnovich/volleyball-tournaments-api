package by.elmax19.app.service.impl;

import by.elmax19.app.exception.TournamentNotFoundException;
import by.elmax19.app.model.Participant;
import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.repository.TournamentRepository;
import by.elmax19.app.service.PlayerService;
import by.elmax19.app.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final PlayerService playerService;
    private final TournamentRepository tournamentRepository;

    @Override
    public List<TournamentDto> findAll() {
        return tournamentRepository.findAll().stream()
                .map(this::mapTournament)
                .toList();
    }

    @Override
    public TournamentDto findById(String id) {
        return tournamentRepository.findById(new ObjectId(id))
                .map(this::mapTournament)
                .orElseThrow(() -> new TournamentNotFoundException("There isn't tournament with id: " + id));
    }

    private TournamentDto mapTournament(Tournament tournament) {
        var participants = tournament.getParticipants().stream()
                .map(this::mapParticipant)
                .toList();
        return TournamentDto.builder()
                .id(tournament.getId().toString())
                .date(tournament.getDate())
                .tournamentName(tournament.getTournamentName())
                .town(tournament.getTown())
                .participants(participants)
                .build();
    }

    private ParticipantDto mapParticipant(Participant participant) {
        var players = playerService.findPlayersByClub(participant.getClubName());
        return ParticipantDto.builder()
                .place(participant.getPlace())
                .clubName(participant.getClubName())
                .players(players)
                .build();
    }
}
