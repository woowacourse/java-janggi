package janggi.domain.team;

public enum Team {
    HAN(-1),
    CHO(1),
    NONE(0);

    public static final Team FIRST_TURN = CHO;

    private final int backwardDirection;

    Team(int backwardDirection) {
        this.backwardDirection = backwardDirection;
    }

    public boolean isBackward(int rowDiff) {
        return Integer.signum(rowDiff) == backwardDirection;
    }

    public Team convert() {
        if (this == Team.HAN) {
            return Team.CHO;
        }

        if (this == Team.CHO) {
            return Team.HAN;
        }

        throw new IllegalArgumentException("[ERROR] 한나라, 초나라만 변환 가능합니다.");
    }
}
