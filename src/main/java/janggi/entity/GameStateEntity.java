package janggi.entity;

import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.stream.Collectors;

public record GameStateEntity(
    long id,
    int turns_taken,
    String team_queue
) {

    public static GameStateEntity from(final int turnsTaken, final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameStateEntity(0, turnsTaken, teamQueue);
    }

    public static GameStateEntity from(final long id, final int turnsTaken,
        final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameStateEntity(id, turnsTaken, teamQueue);
    }
}
