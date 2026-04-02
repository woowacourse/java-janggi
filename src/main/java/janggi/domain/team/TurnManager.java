package janggi.domain.team;

public class TurnManager {
    private TeamType currentTurnTeamType;

    public TurnManager() {
        currentTurnTeamType = TeamType.RED;
    }

    public void changeTurn() {
        this.currentTurnTeamType = currentTurnTeamType.nextTeamType();
    }

    public boolean isCurrentTurnOf(TeamType teamType) {
        return this.currentTurnTeamType == teamType;
    }

    public String currentTeamType() {
        return currentTurnTeamType.getName();
    }
}
