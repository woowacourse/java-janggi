package janggi.domain;

public enum Camp {
    CHO,
    HAN;

    public boolean isSameCamp(Camp camp) {
        return this == camp;
    }

    public Camp next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public boolean isCho() {
        return this == CHO;
    }
}
