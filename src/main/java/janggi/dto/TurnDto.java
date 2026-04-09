package janggi.dto;

import janggi.domain.Turn;
import janggi.domain.team.TeamType;

public record TurnDto(Long id, Long gameId, TeamType currentTurnTeam) {

    public static TurnDto of(long id, long gameId, String currentTurnTeam) {
        return new TurnDto(id, gameId, TeamType.from(currentTurnTeam));
    }

    public static TurnDto from(Turn turn, long gameId) {
        return new TurnDto(null, gameId, turn.getCurrentTeam());
    }

    public String currentTurnTeamName() {
        return currentTurnTeam.getName();
    }
}
