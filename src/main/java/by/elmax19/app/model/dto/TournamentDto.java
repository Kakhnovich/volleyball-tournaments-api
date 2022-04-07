package by.elmax19.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TournamentDto {
    private String id;
    private String tournamentName;
    private LocalDate date;
    private String town;
    private List<ParticipantDto> participants;
}
