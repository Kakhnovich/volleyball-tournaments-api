package by.elmax19.app.service;

import by.elmax19.app.exception.TournamentNotFoundException;
import by.elmax19.app.mapper.ParticipantMapper;
import by.elmax19.app.mapper.TournamentMapper;
import by.elmax19.app.model.Participant;
import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.repository.TournamentRepository;
import by.elmax19.app.service.impl.TournamentServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTests {
    @Mock
    private PlayerService playerService;
    @Mock
    private ParticipantMapper participantMapper;
    @Mock
    private TournamentMapper tournamentMapper;
    @Mock
    private TournamentRepository tournamentRepository;
    @InjectMocks
    private TournamentServiceImpl tournamentService;

    @Test
    @DisplayName("Tournament has been founded by id")
    void checkFindById() {
        Tournament tournament = createTournament();
        TournamentDto tournamentDto = createTournamentDto(tournament.getId().toString());

        when(tournamentRepository.findById(tournament.getId())).thenReturn(Optional.of(tournament));
        when(playerService.findPlayersByClub(tournament.getParticipants().get(0).getClubName()))
                .thenReturn(tournamentDto.getParticipants().get(0).getPlayers());
        when(participantMapper.participantToDto(
                tournament.getParticipants().get(0),
                tournamentDto.getParticipants().get(0).getPlayers()))
                    .thenReturn(tournamentDto.getParticipants().get(0));
        when(tournamentMapper.convertToDto(tournament, tournamentDto.getParticipants())).thenReturn(tournamentDto);

        TournamentDto actual = tournamentService.findById(tournament.getId().toString());

        assertEquals(tournamentDto, actual);
    }

    @Test
    @DisplayName("Exception thrown when no Tournament with such id")
    void checkExceptionFindById() {
        ObjectId id = new ObjectId();
        when(tournamentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TournamentNotFoundException.class, () -> tournamentService.findById(id.toString()));
    }

    @Test
    @DisplayName("All tournaments have been founded")
    void checkFindAll() {
        List<Tournament> tournaments = createTournamentList();
        List<TournamentDto> tournamentDtos = createTournamentDtoList(tournaments);

        when(tournamentRepository.findAll()).thenReturn(tournaments);

        when(playerService.findPlayersByClub(tournaments.get(0).getParticipants().get(0).getClubName()))
                .thenReturn(tournamentDtos.get(0).getParticipants().get(0).getPlayers());
        when(playerService.findPlayersByClub(tournaments.get(0).getParticipants().get(1).getClubName()))
                .thenReturn(tournamentDtos.get(0).getParticipants().get(1).getPlayers());
        when(playerService.findPlayersByClub(tournaments.get(1).getParticipants().get(0).getClubName()))
                .thenReturn(tournamentDtos.get(1).getParticipants().get(0).getPlayers());

        when(participantMapper.participantToDto(
                tournaments.get(0).getParticipants().get(0),
                tournamentDtos.get(0).getParticipants().get(0).getPlayers()))
                    .thenReturn(tournamentDtos.get(0).getParticipants().get(0));
        when(participantMapper.participantToDto(
                tournaments.get(0).getParticipants().get(1),
                tournamentDtos.get(0).getParticipants().get(1).getPlayers()))
                    .thenReturn(tournamentDtos.get(0).getParticipants().get(1));
        when(participantMapper.participantToDto(
                tournaments.get(1).getParticipants().get(0),
                tournamentDtos.get(1).getParticipants().get(0).getPlayers()))
                    .thenReturn(tournamentDtos.get(1).getParticipants().get(0));

        when(tournamentMapper.convertToDto(tournaments.get(0), tournamentDtos.get(0).getParticipants()))
                .thenReturn(tournamentDtos.get(0));
        when(tournamentMapper.convertToDto(tournaments.get(1), tournamentDtos.get(1).getParticipants()))
                .thenReturn(tournamentDtos.get(1));

        List<TournamentDto> actual = tournamentService.findAll();

        assertEquals(actual.size(), tournamentDtos.size());
        assertTrue(tournamentDtos.containsAll(actual));
    }

    private Tournament createTournament() {
        return Tournament.builder()
                .id(new ObjectId())
                .tournamentName("2017 FIVB Volleyball Men's World Grand Champions Cup")
                .date(LocalDate.of(2017, Month.OCTOBER, 11).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        Participant.builder()
                                .place(6)
                                .clubName("Volley Callipo")
                                .build()))
                .build();
    }

    private TournamentDto createTournamentDto(String id) {
        return TournamentDto.builder()
                .id(id)
                .tournamentName("2017 FIVB Volleyball Men's World Grand Champions Cup")
                .date(LocalDate.of(2017, Month.OCTOBER, 11).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        ParticipantDto.builder()
                                .place(6)
                                .clubName("Volley Callipo")
                                .players(List.of(new PlayerDto()))
                                .build()))
                .build();
    }

    private List<Tournament> createTournamentList() {
        List<Tournament> tournaments = new ArrayList<>();
        tournaments.add(Tournament.builder()
                .id(new ObjectId())
                .tournamentName("2019 FIVB Volleyball Men's World Cup")
                .date(LocalDate.of(2019, Month.SEPTEMBER, 30).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        Participant.builder()
                                .place(1)
                                .clubName("Azimut Modena")
                                .build(),
                        Participant.builder()
                                .place(5)
                                .clubName("Zenit Kazan")
                                .build()))
                .build());
        tournaments.add(Tournament.builder()
                .id(new ObjectId())
                .tournamentName("2017 FIVB Volleyball Men's World Grand Champions Cup")
                .date(LocalDate.of(2017, Month.OCTOBER, 11).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        Participant.builder()
                                .place(6)
                                .clubName("Volley Callipo")
                                .build()))
                .build());
        return tournaments;
    }

    private List<TournamentDto> createTournamentDtoList(List<Tournament> tournaments) {
        List<TournamentDto> tournamentDtos = new ArrayList<>();
        tournamentDtos.add(TournamentDto.builder()
                .id(tournaments.get(0).getId().toString())
                .tournamentName("2019 FIVB Volleyball Men's World Cup")
                .date(LocalDate.of(2019, Month.SEPTEMBER, 30).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        ParticipantDto.builder()
                                .place(1)
                                .clubName("Azimut Modena")
                                .players(List.of(new PlayerDto()))
                                .build(),
                        ParticipantDto.builder()
                                .place(5)
                                .clubName("Zenit Kazan")
                                .players(List.of(new PlayerDto()))
                                .build()))
                .build());
        tournamentDtos.add(TournamentDto.builder()
                .id(tournaments.get(1).getId().toString())
                .tournamentName("2017 FIVB Volleyball Men's World Grand Champions Cup")
                .date(LocalDate.of(2017, Month.OCTOBER, 11).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        ParticipantDto.builder()
                                .place(6)
                                .clubName("Volley Callipo")
                                .players(List.of(new PlayerDto()))
                                .build()))
                .build());
        return tournamentDtos;
    }
}
