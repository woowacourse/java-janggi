package pieces;

public enum Side {
    HAN, CHO;

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }
}
