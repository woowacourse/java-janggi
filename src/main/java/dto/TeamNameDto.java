package dto;

import domain.piece.Team;

public record TeamNameDto(String name) {

    public static TeamNameDto of(Team team) {
        return new TeamNameDto(team.toString());
    }
}
