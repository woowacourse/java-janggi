package janggi.dto;

import janggi.domain.turn.Turn;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnStatus;

public record TurnDto(Long id, Long gameId, TeamType currentTurnTeam, TurnStatus turnStatus) {

    public static TurnDto of(long id, long gameId, String currentTurnTeam, String turnStatus) {
        return new TurnDto(id, gameId, TeamType.from(currentTurnTeam), TurnStatus.from(turnStatus));
    }

    public static TurnDto from(Turn turn, long gameId) {
        return new TurnDto(turn.getId(), gameId, turn.getCurrentTeam(), turn.getTurnStatus());
    }

    public String currentTurnTeamName() {
        return currentTurnTeam.getName();
    }

    public String turnStatusFormat() {
        return turnStatus.getFormat();
    }
}
