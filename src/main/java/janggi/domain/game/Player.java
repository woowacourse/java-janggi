package janggi.domain.game;

import janggi.domain.piece.Piece;

public record Player(String name, Side side) {

    public boolean isMyTurn(Turn turn) {
        return turn.isTurnOf(side);
    }

    public boolean isOwnPiece(Piece piece) {
        return piece.isBelongTo(side);
    }
}
