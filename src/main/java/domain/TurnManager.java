package domain;

public class TurnManager {

    TeamColor teamColor;
    int moveCount;

    public TurnManager(){
        teamColor = TeamColor.CHO;
        moveCount = 0;
    }

    public void progressTurn(){
        moveCount += 1;
        if(teamColor.equals(TeamColor.CHO)) {
            teamColor = TeamColor.HAN;
            return;
        }
        teamColor = TeamColor.CHO;
    }

    public TeamColor getCurrentTurn() {
        return teamColor;
    }
}
