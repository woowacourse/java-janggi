package domain.player;

public enum Team {
    CHO,
    HAN;

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }
}
