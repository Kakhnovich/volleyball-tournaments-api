package by.elmax19.app.mapper;

import by.elmax19.app.model.Tournament;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.TournamentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", uses = ParticipantMapper.class, imports = ZoneOffset.class)
public interface TournamentMapper {
    @Mapping(target = "id", expression = "java(tournament.getId().toString())")
    @Mapping(target = "date", expression = "java(tournament.getDate().atStartOfDay().toInstant(ZoneOffset.UTC))")
    @Mapping(target = "participants", source = "participants")
    TournamentDto convertToDto(Tournament tournament, List<ParticipantDto> participants);
}
