package janggi.domain.team;

import janggi.domain.piece.Piece;

public class TurnManager {
    private TeamType currentTurnTeamType;

    public TurnManager() {
        currentTurnTeamType = TeamType.RED;
    }

    public void changeTurn() {
        this.currentTurnTeamType = currentTurnTeamType.nextTeamType();
    }

    public boolean isCurrentTurnOf(Piece piece) {
        return piece.getTeamType() == currentTurnTeamType;
    }

    public String currentTeamType() {
        return currentTurnTeamType.getName();
    }
}
