package domain.game;

import domain.piece.TeamColor;

public class TurnManager {

    private TeamColor currentTurn;

    public TurnManager() {
        this(TeamColor.CHO);
    }

    public TurnManager(TeamColor currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void advanceTurn() {
        currentTurn = currentTurn.next();
    }

    public TeamColor getCurrentTurn() {
        return currentTurn;
    }
}

