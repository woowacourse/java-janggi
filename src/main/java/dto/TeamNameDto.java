package dto;

import domain.piece.Team;

public record TeamNameDto(String name) {

    public static TeamNameDto of(final Team team) {
        return new TeamNameDto(team.toString());
    }
}
