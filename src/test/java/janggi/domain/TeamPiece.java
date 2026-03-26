package janggi.domain;

import janggi.domain.piece.Piece;

public class TeamPiece extends Piece {

    public TeamPiece(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
