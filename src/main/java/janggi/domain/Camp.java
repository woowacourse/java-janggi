package janggi.domain;

public enum Camp {
    CHO(1),
    HAN(-1);

    private final int direction;

    Camp(int direction) {
        this.direction = direction;
    }

    public int direction() {
        return direction;
    }

    public boolean isCho(){
        return this == CHO;
    }
}
