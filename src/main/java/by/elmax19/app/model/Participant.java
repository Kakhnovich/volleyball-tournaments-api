package by.elmax19.app.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class Participant {
    private int place;
    private String clubName;
}
