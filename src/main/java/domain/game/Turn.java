package domain.game;

import domain.piece.Piece;
import domain.piece.Team;

public enum Turn {

    CHO(Team.CHO, "초"),
    HAN(Team.HAN, "한"),
    ;

    private static final String ERROR_NOT_SAME_TEAM = "본인의 진영의 기물이 아닙니다.";

    private final Team team;
    private final String name;

    Turn(Team team, String name) {
        this.team = team;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Team team() {
        return team;
    }

    public Turn reverse() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }

    public void validateSameTeam(Piece piece) {
        if (!piece.isSameTeam(this.team())) {
            throw new IllegalArgumentException(ERROR_NOT_SAME_TEAM);
        }
    }
}
