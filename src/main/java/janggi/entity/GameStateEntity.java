package janggi.entity;

import janggi.domain.team.Team;
import janggi.domain.turn.TurnManager;
import java.util.stream.Collectors;

public record GameStateEntity(
    long id,
    int turns_taken,
    String team_queue
) {

    public static GameStateEntity from(final TurnManager turnManager) {
        final String teamQueue = turnManager.getTeams()
            .stream()
            .map(Team::getTeamType)
            .map(Enum::name)
            .collect(Collectors.joining(","));
        return new GameStateEntity(0, turnManager.getTurnTaken(), teamQueue);
    }

    public static GameStateEntity from(final int id, final TurnManager turnManager) {
        final String teamQueue = turnManager.getTeams()
            .stream()
            .map(Team::getTeamType)
            .map(Enum::name)
            .collect(Collectors.joining(","));
        return new GameStateEntity(id, turnManager.getTurnTaken(), teamQueue);
    }
}
