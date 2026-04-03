package domain.game;

import domain.piece.TeamColor;

public class TurnManager {

    private TeamColor currentTurn;

    public TurnManager() {
        currentTurn = TeamColor.CHO;
    }

    public void advanceTurn() {
        if (currentTurn.equals(TeamColor.CHO)) {
            currentTurn = TeamColor.HAN;
            return;
        }
        currentTurn = TeamColor.CHO;
    }

    public TeamColor getCurrentTurn() {
        return currentTurn;
    }
}


