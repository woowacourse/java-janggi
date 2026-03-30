package domain.piece;

public enum Camp {
    CHO(-1),
    HAN(1);

    private final int forward;

    Camp(int forward) {
        this.forward = forward;
    }

    public int forward() {
        return this.forward;
    }

    public Camp nextTurn() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
