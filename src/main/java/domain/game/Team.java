package domain.game;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    public int forwardRowDirection() {
        if (this == CHO) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
