package janggi.domain.piece;

public enum Side {
    RED,
    BLUE;

    public Side opposite() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }
}
