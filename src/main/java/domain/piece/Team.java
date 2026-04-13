package domain.piece;

public enum Team {

    CHO,
    HAN,
    NONE;

    public Team nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public boolean isHan() {
        return this == HAN;
    }

}
