package domain.piece;

public enum Team {
    CHO, HAN;

    public boolean isCho() {
        return this == CHO;
    }

    public boolean isHan() {
        return this == HAN;
    }
}
