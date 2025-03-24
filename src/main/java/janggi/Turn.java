package janggi;

import janggi.piece.Side;

public record Turn(Side side) {

    public static Turn firstTurn() {
        return new Turn(Side.BLUE);
    }

    public Turn nextTurn() {
        return new Turn(this.side.opposite());
    }
}
