package domain;

public enum Side {

    HAN,
    CHU;

    public boolean isChu() {
        return this == CHU;
    }

    public boolean isHan() {
        return this == HAN;
    }
}
