package janggi.domain.game;

import janggi.domain.piece.Side;

public class Turn {

    private Side side;

    public Turn(final Side side) {
        this.side = side;
    }

    public static Turn firstTurn() {
        return new Turn(Side.BLUE);
    }

    public void nextTurn() {
        this.side = this.side.opposite();
    }

    public Side getSide() {
        return side;
    }
}
