package janggi.domain.piece;

public enum Side {
    HAN, CHO;

    public static final double SECOND_TURN_BONUS = 1.5;

    public static Side getFirstTurn() {
        return CHO;
    }

    public static Side opposite(Side side) {
        if (side == HAN) return CHO;
        return HAN;
    }

    public boolean isSecondTurn() {
        return this == HAN;
    }

    public double plusSecondTurnBonus(double sideScore) {
        return sideScore + SECOND_TURN_BONUS;
    }
}
