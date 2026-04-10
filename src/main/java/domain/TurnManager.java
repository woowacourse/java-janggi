package domain;

public class TurnManager {

    private TeamColor currentTeamColor;

    public TurnManager() {
    }

    public TurnManager(TeamColor startingTeamColor) {
        this.currentTeamColor = startingTeamColor;
    }

    public void start() {
        this.currentTeamColor = TeamColor.CHO;
    }

    public void progressTurn(){
        if(currentTeamColor.equals(TeamColor.CHO)) {
            this.currentTeamColor = TeamColor.HAN;
            return;
        }
        this.currentTeamColor = TeamColor.CHO;
    }

    public TeamColor getCurrentTurn() {
        return this.currentTeamColor;
    }
}
