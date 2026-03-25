package domain.player;

public enum Team {
    CHO(1),
    HAN(-1);

    private final int direction;

    Team(int direction) {
        this.direction = direction;
    }
}
