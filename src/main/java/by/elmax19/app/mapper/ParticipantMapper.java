package by.elmax19.app.mapper;

import by.elmax19.app.model.Participant;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.model.dto.PlayerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {
    @Mapping(target = "players", source = "players")
    public abstract ParticipantDto participantToDto(Participant participant, List<PlayerDto> players);
}
