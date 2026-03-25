package domain.piece;

import domain.game.Side;

public abstract class Piece {

    protected final Side side;

    public Piece(Side side) {
        this.side = side;
    }
}
