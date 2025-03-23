package janggi.domain;

import janggi.domain.piece.PieceBehavior;

public enum Side {
    HAN,
    CHO;

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String toName(PieceBehavior pieceBehavior) {
        if (this == Side.CHO) {
            return "\u001B[32m" + pieceBehavior.toName() + "\u001B[0m";
        }
        return "\u001B[31m" + pieceBehavior.toName() + "\u001B[0m";
    }
}
