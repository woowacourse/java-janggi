package janggi.domain;

import janggi.domain.piece.PieceBehavior;

public enum Side {
    HAN,
    CHO;

    private static final String CHO_COLOR_FORMAT = "\u001B[32m%s\u001B[0m";
    private static final String HAN_COLOR_FORMAT = "\u001B[31m%s\u001B[0m";

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String toName(PieceBehavior pieceBehavior) {
        return toColorString(pieceBehavior.toName());
    }

    public String toColorString(String message) {
        if (this == Side.CHO) {
            return String.format(CHO_COLOR_FORMAT, message);
        }
        return String.format(HAN_COLOR_FORMAT, message);
    }
}
