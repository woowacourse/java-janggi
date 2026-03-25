package janggi.domain.piece;

import janggi.domain.Side;

public class Cha extends Piece {
    public Cha(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
