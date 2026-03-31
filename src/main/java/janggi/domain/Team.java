package janggi.domain;

public enum Team {
    HAN("한", -1),
    CHO("초", 1),
    NONE("무", 0),
    ;

    private final String displayName;
    private final int backwardDirection;

    Team(String displayName, int backwardDirection) {
        this.displayName = displayName;
        this.backwardDirection = backwardDirection;
    }

    public boolean isBackward(int rowDiff) {
        return Integer.signum(rowDiff) == backwardDirection;
    }

    public Team convert() {
        if (this == Team.HAN) return Team.CHO;
        if (this == Team.CHO) return Team.HAN;
        return Team.NONE;
    }

    public String getDisplayName() {
        return displayName;
    }
}
