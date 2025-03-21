package team;

public enum Team {

    HAN(1),
    CHO(-1),
    ;

    private final int direction;

    Team(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
