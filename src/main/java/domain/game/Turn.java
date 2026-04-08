package domain.game;

import domain.game.exception.GameErrorMessage;
import domain.pieces.Piece;
import domain.pieces.Side;

public record Turn(Side side) {

    public static Turn start() {
        return new Turn(Side.CHO);
    }

    public static Turn from(Side side) {
        return new Turn(side);
    }

    public boolean isNotCurrentTurnPiece(Piece piece) {
        if (side.isCho()) {
            return piece.isHan();
        }
        return piece.isCho();
    }

    public GameErrorMessage errorMessage() {
        if (side.isCho()) {
            return GameErrorMessage.CHO_TURN;
        }
        return GameErrorMessage.HAN_TURN;
    }

    public Turn next() {
        if (side.isCho()) {
            return new Turn(Side.HAN);
        }
        return new Turn(Side.CHO);
    }
}
