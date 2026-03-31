package janggi.domain.team;

public class TurnManager {
    private final Team redTeam;
    private final Team blueTeam;
    private TeamType currentTurnTeamType;

    public TurnManager(Team redTeam, Team blueTeam) {
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        currentTurnTeamType = TeamType.RED;
    }

    public void changeTurn() {
        this.currentTurnTeamType = currentTurnTeamType.nextTeamType();
    }
}
