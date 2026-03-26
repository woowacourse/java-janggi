package janggi.domain.piece;

import janggi.domain.Side;

public class EmptyPiece extends Piece {

    private static final String PIECE_NAME = "ㆍ";

    public EmptyPiece() {
        super(PIECE_NAME, Side.NONE);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
