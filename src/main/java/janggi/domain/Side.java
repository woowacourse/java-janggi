package janggi.domain;

public enum Side {
    HAN,
    CHO;

    public Side reverse() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
