package domain;

public enum Side {

    HAN,
    CHU,
    NEUTRAL;

    public boolean isChu() {
        return this == CHU;
    }

    public boolean isHan() {
        return this == HAN;
    }
}
