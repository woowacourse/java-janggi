package team;

public enum Team {

    RED(-1),
    GREEN(1);

    private final int direction;

    Team(int direction) {
        this.direction = direction;
    }

    public int direction() {
        return direction;
    }
}
