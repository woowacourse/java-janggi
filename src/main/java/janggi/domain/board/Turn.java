package janggi.domain.board;

import janggi.domain.piece.Side;

class Turn {

    private final Side value;

    private Turn(Side value) {
        this.value = value;
    }

    static Turn start() {
        return new Turn(Side.HAN);
    }

    Turn next() {
        if (value == Side.HAN) {
            return new Turn(Side.CHO);
        }
        return new Turn(Side.HAN);
    }

    boolean isTurn(Side side) {
        return value == side;
    }
}
