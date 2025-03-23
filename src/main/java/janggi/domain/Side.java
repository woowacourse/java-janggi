package janggi.domain;

public enum Side {
    HAN, CHO;

    public static Side getFirstTurn() {
        return HAN;
    }

    public static Side opposite(Side side) {
        if (side == HAN) return CHO;
        return HAN;
    }
}
