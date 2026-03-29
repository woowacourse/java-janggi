package domain.game;

import domain.piece.TeamColor;

public class TurnManager {

    private TeamColor currentTurn;
    private int moveCount;

    public TurnManager() {
        currentTurn = TeamColor.CHO;
        moveCount = 0;
    }

    public void advanceTurn() {
        moveCount += 1;
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



