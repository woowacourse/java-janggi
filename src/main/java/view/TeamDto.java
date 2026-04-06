package view;

import domain.piece.Team;

public record TeamDto(
        String teamName
) {
    public static TeamDto toDto(Team team) {
        if (team.equals(Team.HAN)) {
            return new TeamDto("한");
        }

        if (team.equals(Team.CHO)) {
            return new TeamDto("초");
        }

        throw new IllegalStateException(ViewErrorMessage.NOT_DEFINED_TEAM.getMessage());
    }
}
