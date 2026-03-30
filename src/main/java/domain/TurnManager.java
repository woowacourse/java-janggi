package domain;

public class TurnManager {

    private TeamColor teamColor;

    public TurnManager(){
        this.teamColor = TeamColor.CHO;
    }

    public void progressTurn(){
        if(teamColor.equals(TeamColor.CHO)) {
            this.teamColor = TeamColor.HAN;
            return;
        }
        this.teamColor = TeamColor.CHO;
    }

    public TeamColor getCurrentTurn() {
        return this.teamColor;
    }
}
