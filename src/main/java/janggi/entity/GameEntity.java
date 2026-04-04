package janggi.entity;

import janggi.domain.team.TeamType;
import java.util.List;
import java.util.stream.Collectors;

public record GameEntity(
    long id,
    String name,
    int turns_taken,
    String team_queue
) {

    public static GameEntity from(final String name, final int turnsTaken,
        final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(0, name, turnsTaken, teamQueue);
    }

    public static GameEntity from(final long id, final String name, final int turnsTaken,
        final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(id, name, turnsTaken, teamQueue);
    }
}
