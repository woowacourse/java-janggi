package domain.piece;

public enum Team {

    CHO(0),
    HAN(1.5),
    NONE(0),
    ;

    private static final String NO_REVERSE_TEAM_MESSAGE = "변경이 불가능한 팀입니다.";

    private final double bonus;

    Team(double bonus) {
        this.bonus = bonus;
    }

    public double bonus() {
        return bonus;
    }

    public Team reverse() {
        if (this == Team.CHO) return Team.HAN;
        if (this == Team.HAN) return Team.CHO;
        throw new IllegalStateException(NO_REVERSE_TEAM_MESSAGE);
    }
}
