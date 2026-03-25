package domain.player;

public enum Team {
    CHO(1),
    HAN(-1);

    private final int direction;

    Team(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isHan() {
        return this == HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }
}
