package janggi.domain.piece;

import janggi.domain.Side;

public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super(Side.NONE);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

}
