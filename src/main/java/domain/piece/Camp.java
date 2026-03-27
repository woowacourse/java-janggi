package domain.piece;

public enum Camp {
    CHO(1),
    HAN(-1);

    private final int forward;

    Camp(int forward) {
        this.forward = forward;
    }
}
