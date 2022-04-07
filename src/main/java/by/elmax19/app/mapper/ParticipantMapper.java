package by.elmax19.app.mapper;

import by.elmax19.app.model.Participant;
import by.elmax19.app.model.dto.ParticipantDto;
import by.elmax19.app.service.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {
    @Autowired
    protected PlayerService playerService;

    @Mapping(target = "players", expression = "java(playerService.findPlayersByClub(participant.getClubName()))")
    public abstract ParticipantDto participantToDto(Participant participant);

    public abstract List<ParticipantDto> participantListToDto(List<Participant> participants);
}
