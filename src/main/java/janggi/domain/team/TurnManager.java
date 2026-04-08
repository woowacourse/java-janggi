package janggi.domain.team;

public class TurnManager {
    private TeamType currentTurnTeamType;

    public TurnManager() {
        currentTurnTeamType = TeamType.RED;
    }

    public TurnManager(String teamType) {
        currentTurnTeamType = TeamType.valueOf(teamType);
    }

    public void changeTurn() {
        this.currentTurnTeamType = currentTurnTeamType.nextTeamType();
    }

    public boolean isCurrentTurnOf(TeamType teamType) {
        return this.currentTurnTeamType == teamType;
    }

    public TeamType currentTeamType() {
        return currentTurnTeamType;
    }

    public String currentTeamTypeToName() {
        return currentTurnTeamType.getName();
    }
}
