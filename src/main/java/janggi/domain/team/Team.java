package janggi.domain.team;

public enum Team {
    HAN(-1),
    CHO(1);

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

        throw new IllegalArgumentException("[ERROR] 한나라, 초나라만 선택 가능합니다.");
    }
}
