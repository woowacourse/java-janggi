package domain.game;

import domain.piece.Piece;
import domain.piece.Team;

public enum Turn {

    CHO(true, "초"),
    HAN(false, "한"),
    ;

    private static final String ERROR_NOT_SAME_TEAM = "본인의 진영의 기물이 아닙니다.";

    private final boolean isCho;
    private final String name;

    Turn(boolean isCho, String name) {
        this.isCho = isCho;
        this.name = name;
    }

    public boolean isCho() {
        return isCho;
    }

    public String getName() {
        return name;
    }

    public Team team() {
        if (this.isCho == CHO.isCho()) {
            return Team.CHO;
        }
        return Team.HAN;
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
