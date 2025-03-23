package domain.piece;

public enum JanggiSide {
    CHO,
    HAN,
    NONE;

    public JanggiSide getOppositeSide() {
        if (this == CHO) {
            return HAN;
        }
        if (this == HAN) {
            return CHO;
        }
        return NONE;
    }
}
