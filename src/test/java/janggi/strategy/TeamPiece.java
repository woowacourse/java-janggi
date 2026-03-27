package janggi.strategy;

import janggi.domain.piece.Piece;

public class TeamPiece extends Piece {

    private static final String PIECE_NAME = "test";

    public TeamPiece(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
