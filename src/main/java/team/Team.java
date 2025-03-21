package team;

public enum Team {

    RED(-1),
    GREEN(1),
    ;

    private final int dir;

    Team(int dir) {
        this.dir = dir;
    }

    public int direction() {
        return dir;
    }
}
