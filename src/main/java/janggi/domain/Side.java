package janggi.domain;

public enum Side {
    HAN,
    CHO;

    public static Side from(Side side) {
        if (side == HAN) {
            return HAN;
        }
        return CHO;
    }

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }

        return HAN;
    }
}
