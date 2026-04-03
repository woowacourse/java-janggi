package janggi.domain;

public enum Side {

    HAN,
    CHO;

    public Side switchTurn() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
