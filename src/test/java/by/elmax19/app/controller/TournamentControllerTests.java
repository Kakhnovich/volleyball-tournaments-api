package by.elmax19.app.controller;

import by.elmax19.app.client.PlayerClient;
import by.elmax19.app.model.Participant;
import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.PlayerDto;
import by.elmax19.app.model.dto.TournamentDto;
import by.elmax19.app.repository.TournamentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TournamentControllerTests {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerClient playerClient;

    @Test
    @DisplayName("All tournaments have been founded")
    void checkFindAllTournaments() throws Exception {
        List<TournamentDto> expectedTournaments = initTournamentList();

        when(playerClient.findPlayersByClub("Azimut Modena")).thenReturn(List.of(createNimirDto()));
        when(playerClient.findPlayersByClub("Zenit Kazan")).thenReturn(List.of(createMikhaylovDto()));
        when(playerClient.findPlayersByClub("Volley Callipo")).thenReturn(List.of(createNishidaDto()));

        MvcResult mvcResult = mockMvc.perform(get("/tournaments"))
                .andExpect(status().isOk())
                .andReturn();
        List<TournamentDto> actualTournaments = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        assertEquals(2, actualTournaments.size());
        compareTournaments(expectedTournaments.get(0), actualTournaments.get(0));
        compareTournaments(expectedTournaments.get(1), actualTournaments.get(1));

        clearDatabaseData();
    }

    @Test
    @DisplayName("Tournament has been founded by id")
    void checkFindTournamentById() throws Exception {
        TournamentDto expectedTournament = initTournamentDto();

        when(playerClient.findPlayersByClub("Azimut Modena")).thenReturn(List.of(createNimirDto()));
        when(playerClient.findPlayersByClub("Zenit Kazan")).thenReturn(List.of(createMikhaylovDto()));

        MvcResult mvcResult = mockMvc.perform(get("/tournament/{tournamentId}", expectedTournament.getId()))
                .andExpect(status().isOk())
                .andReturn();
        TournamentDto actualTournament = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                });

        compareTournaments(expectedTournament, actualTournament);

        clearDatabaseData();
    }

    @Test
    @DisplayName("Tournament has not been founded by id")
    void checkNotFoundTournamentById() throws Exception {
        ObjectId searchedId = new ObjectId();
        mockMvc.perform(get("/tournaments/{tournamentId}", searchedId.toString()))
                .andExpect(status().isNotFound());
    }

    void clearDatabaseData() {
        tournamentRepository.deleteAll();
    }

    private void compareTournaments(TournamentDto firstTournament, TournamentDto secondTournament) {
        assertEquals(firstTournament.getId(), secondTournament.getId());
        assertEquals(firstTournament.getTournamentName(), secondTournament.getTournamentName());
        assertEquals(firstTournament.getDate(), secondTournament.getDate());
        assertEquals(firstTournament.getTown(), secondTournament.getTown());
        assertEquals(firstTournament.getParticipants().size(), secondTournament.getParticipants().size());
        for (int i = 0; i < firstTournament.getParticipants().size(); i++) {
            assertEquals(firstTournament.getParticipants().get(0).getClubName(),
                    secondTournament.getParticipants().get(0).getClubName());
            assertEquals(firstTournament.getParticipants().get(0).getPlace(),
                    secondTournament.getParticipants().get(0).getPlace());
        }
    }

    private TournamentDto initTournamentDto() {
        Tournament tournament = Tournament.builder()
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
                .build();
        tournamentRepository.save(tournament);

        return TournamentDto.builder()
                .id(tournament.getId().toString())
                .tournamentName("2019 FIVB Volleyball Men's World Cup")
                .date(LocalDate.of(2019, Month.SEPTEMBER, 30).atStartOfDay().toInstant(ZoneOffset.UTC))
                .town("Japan")
                .participants(List.of(
                        ParticipantDto.builder()
                                .place(1)
                                .clubName("Azimut Modena")
                                .players(List.of(createNimirDto()))
                                .build(),
                        ParticipantDto.builder()
                                .place(5)
                                .clubName("Zenit Kazan")
                                .players(List.of(createMikhaylovDto()))
                                .build()))
                .build();
    }


    private List<TournamentDto> initTournamentList() {
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
        tournamentRepository.saveAll(tournaments);

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
                                .players(List.of(createNimirDto()))
                                .build(),
                        ParticipantDto.builder()
                                .place(5)
                                .clubName("Zenit Kazan")
                                .players(List.of(createMikhaylovDto()))
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
                                .players(List.of(createNishidaDto()))
                                .build()))
                .build());
        return tournamentDtos;
    }

    private PlayerDto createNimirDto() {
        return PlayerDto.builder()
                .id(new ObjectId().toString())
                .fullName("Nimir Abdel-Aziz")
                .age(30)
                .height(2.01)
                .spike(360)
                .block(340)
                .position("OPPOSITE_HITTER")
                .club("Azimut Modena")
                .number(14)
                .build();
    }

    private PlayerDto createMikhaylovDto() {
        return PlayerDto.builder()
                .id(new ObjectId().toString())
                .fullName("Maxim Mikhaylov")
                .age(33)
                .height(2.02)
                .spike(360)
                .block(340)
                .position("OPPOSITE_HITTER")
                .club("Zenit Kazan")
                .number(18)
                .nationalities(List.of("Russian"))
                .build();
    }

    private PlayerDto createNishidaDto() {
        return PlayerDto.builder()
                .id(new ObjectId().toString())
                .fullName("Yuji Nishida")
                .age(22)
                .height(1.86)
                .spike(350)
                .block(335)
                .position("OPPOSITE_HITTER")
                .club("Volley Callipo")
                .number(2)
                .nationalities(List.of("Japanese"))
                .build();
    }
}
