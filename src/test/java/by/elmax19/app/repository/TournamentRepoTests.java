package by.elmax19.app.repository;

import by.elmax19.app.model.Tournament;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TournamentRepoTests {
    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    @DisplayName("Tournament has been added")
    void checkTournamentCreation() {
        Tournament tournament = createNewTournament();
        long countOfDocumentBeforeCreation = tournamentRepository.count();

        tournamentRepository.save(tournament);

        assertEquals(countOfDocumentBeforeCreation + 1, tournamentRepository.count());
    }

    @Test
    @DisplayName("Tournament has been deleted")
    void checkTournamentRemoval() {
        Tournament tournament = createNewTournament();
        tournamentRepository.save(tournament);
        long countOfDocumentBeforeRemoval = tournamentRepository.count();

        tournamentRepository.delete(tournament);

        assertEquals(countOfDocumentBeforeRemoval - 1, tournamentRepository.count());
    }

    @Test
    @DisplayName("Tournament has been found by id")
    void checkTournamentFindingById() {
        Tournament tournament = createNewTournament();
        tournamentRepository.save(tournament);
        Optional<Tournament> foundedTournament = tournamentRepository.findById(tournament.getId());
        assertTrue(foundedTournament.isPresent());
        assertEquals(tournament, foundedTournament.get());
    }

    @Test
    @DisplayName("All tournaments have been founded")
    void checkFindingAllTournaments() {
        List<Tournament> tournaments = List.of(createNewTournament(), createNewTournament());
        tournamentRepository.saveAll(tournaments);
        List<Tournament> foundedTournaments = tournamentRepository.findAll();
        assertEquals(tournaments.size(), foundedTournaments.size());
        assertTrue(tournaments.containsAll(foundedTournaments));
    }

    private Tournament createNewTournament() {
        EasyRandom generator = new EasyRandom();
        return generator.nextObject(Tournament.class);
    }

    @AfterAll
    public void cleanUp() {
        tournamentRepository.deleteAll();
    }
}
