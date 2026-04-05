package janggi.domain.piece;

import janggi.domain.Side;

public record PieceAttribute(Side side, PieceType pieceType) {
    public String getOppositeSideName() {
        return side.getOppositeSideName();
    }
}
