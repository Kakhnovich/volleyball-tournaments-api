package by.elmax19.app.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@Builder
@Getter
@Document(collection = "tournaments")
public class Tournament {
    @MongoId
    private ObjectId id;
    private String tournamentName;
    private LocalDate date;
    private String town;
    private List<Participant> participants;
}
