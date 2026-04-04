package domain.piece;

public enum Team {

    CHO,
    HAN,
    NONE,
    ;

    private static final String NO_REVERSE_TEAM_MESSAGE = "변경이 불가능한 팀입니다.";

    public Team reverse() {
        if (this == Team.CHO) return Team.HAN;
        if (this == Team.HAN) return Team.CHO;
        throw new IllegalStateException(NO_REVERSE_TEAM_MESSAGE);
    }
}
