package janggi.dto;

import janggi.domain.side.TeamType;

public record TurnDto(Long id, Long gameId, TeamType currentTurnTeam) {

    public static TurnDto of(long id, long gameId, String currentTurnTeam) {
        return new TurnDto(id, gameId, TeamType.from(currentTurnTeam));
    }

    public String currentTurnTeamName() {
        return currentTurnTeam.getName();
    }
}
