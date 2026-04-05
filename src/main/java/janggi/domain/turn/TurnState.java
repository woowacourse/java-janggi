package janggi.domain.turn;

import janggi.domain.Side;
import janggi.domain.piece.PieceAttribute;

public record TurnState(PlayerTurn playerTurn, TurnAttribute turnAttribute, PieceAttribute movedPiece) {
    public Side getNextTurnSide() {
        return turnAttribute.side();
    }

    public int getNextTurn() {
        return turnAttribute.turn();
    }
}
