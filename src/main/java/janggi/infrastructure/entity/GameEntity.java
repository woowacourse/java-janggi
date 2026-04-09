package janggi.infrastructure.entity;

import janggi.domain.game.GameStatus;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.stream.Collectors;

public record GameEntity(
    long id,
    String name,
    int turnsTaken,
    String teamQueue,
    String status
) {

    public static GameEntity from(final String name, final int turnsTaken,
        final List<TeamType> teams, final GameStatus gameStatus) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(0, name, turnsTaken, teamQueue, gameStatus.name());
    }

    public static GameEntity from(final long id, final String name, final int turnsTaken,
        final List<TeamType> teams, final GameStatus gameStatus) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(id, name, turnsTaken, teamQueue, gameStatus.name());
    }

    public static GameEntity from(final String name, final int turnsTaken,
        final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(0, name, turnsTaken, teamQueue, GameStatus.IN_PROGRESS.name());
    }

    public static GameEntity from(final long id, final String name, final int turnsTaken,
        final List<TeamType> teams) {
        final String teamQueue = teams.stream()
            .map(TeamType::name)
            .collect(Collectors.joining(","));
        return new GameEntity(id, name, turnsTaken, teamQueue, GameStatus.IN_PROGRESS.name());
    }
}
