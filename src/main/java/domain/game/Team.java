package domain.game;

public enum Team {
    CHO("초", 1),
    HAN("한", -1),
    NONE("", 0);

    private final String teamName;
    private final int forwardRowDirection;

    Team(String teamName, int forwardRowDirection) {
        this.teamName = teamName;
        this.forwardRowDirection = forwardRowDirection;
    }

    public int forwardRowDirection() {
        return forwardRowDirection;
    }

    @Override
    public String toString() {
        return teamName;
    }
}
