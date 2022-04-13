package by.elmax19.app.repository;

import by.elmax19.app.model.Tournament;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentRepository extends CrudRepository<Tournament, ObjectId> {
    List<Tournament> findAll();
}
