package by.elmax19.app.service.impl;

import by.elmax19.app.exception.TournamentNotFoundException;
import by.elmax19.app.mapper.ParticipantMapper;
import by.elmax19.app.mapper.TournamentMapper;
import by.elmax19.app.model.Participant;
import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.repository.TournamentRepository;
import by.elmax19.app.service.PlayerService;
import by.elmax19.app.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {
    private final PlayerService playerService;
    private final TournamentRepository tournamentRepository;
    private final ParticipantMapper participantMapper;
    private final TournamentMapper tournamentMapper;

    @Override
    public List<TournamentDto> findAll() {
        List<Tournament> allTournaments = tournamentRepository.findAll();
        return convertTournamentListToDto(allTournaments);
    }

    @Override
    public TournamentDto findById(String id) {
        return tournamentRepository.findById(new ObjectId(id))
                .map(this::convertTournamentToDto)
                .orElseThrow(() -> new TournamentNotFoundException("There isn't tournament with id: " + id));
    }

    private List<TournamentDto> convertTournamentListToDto(List<Tournament> tournaments) {
        List<TournamentDto> tournamentDtos = new ArrayList<>();
        for (Tournament tournament : tournaments) {
            TournamentDto tournamentDto = convertTournamentToDto(tournament);
            tournamentDtos.add(tournamentDto);
        }
        return tournamentDtos;
    }

    private TournamentDto convertTournamentToDto(Tournament tournament) {
        List<ParticipantDto> participantDtos = new ArrayList<>();
        for (Participant participant : tournament.getParticipants()) {
            ParticipantDto participantDto = convertParticipantToDto(participant);
            participantDtos.add(participantDto);
        }
        return tournamentMapper.convertToDto(tournament, participantDtos);
    }

    private ParticipantDto convertParticipantToDto(Participant participant) {
        List<PlayerDto> players = playerService.findPlayersByClub(participant.getClubName());
        return participantMapper.participantToDto(participant, players);
    }
}
