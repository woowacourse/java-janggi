package janggi.domain;

public enum Camp {
    CHO(1, 0),
    HAN(-1, 9);

    private final int direction;
    private final int initRowPosition;

    Camp(int direction, int initRowPosition) {
        this.direction = direction;
        this.initRowPosition = initRowPosition;
    }

    public Camp next() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public boolean isSameCamp(Camp camp) {
        return this == camp;
    }

    public boolean isCho() {
        return this == CHO;
    }

    public int direction() {
        return direction;
    }

    public int initRowPosition() {
        return initRowPosition;
    }
}
