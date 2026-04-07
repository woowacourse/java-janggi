package domain.game;

import domain.piece.TeamColor;

public class TurnManager {

    private TeamColor currentTurn;

    public TurnManager() {
        currentTurn = TeamColor.CHO;
    }

    public void advanceTurn() {
        currentTurn = currentTurn.next();
    }

    public TeamColor getCurrentTurn() {
        return currentTurn;
    }
}


