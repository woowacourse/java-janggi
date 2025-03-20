package dto;

import java.util.Arrays;

import model.Team;

public enum TeamDto {
    CHO("초나라"),
    HAN("한나라");

    private final String displayName;

    TeamDto(String displayName) {
        this.displayName = displayName;
    }

    public static TeamDto from(Team currentTurn) {
        return Arrays.stream(values())
            .filter(teamDto -> teamDto.name().equals(currentTurn.name()))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

    public String getDisplayName() {
        return displayName;
    }
}
