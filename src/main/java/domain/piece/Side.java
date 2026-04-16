package domain.piece;

public enum Side {
    CHO,
    HAN;

    public Side opposite() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
