package by.elmax19.app.mapper;

import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.TournamentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ParticipantMapper.class)
public interface TournamentMapper {
    @Mapping(target = "id", expression = "java(tournament.getId().toString())")
    TournamentDto convertToDto(Tournament tournament);

    List<TournamentDto> convertListToDto(List<Tournament> tournaments);
}
