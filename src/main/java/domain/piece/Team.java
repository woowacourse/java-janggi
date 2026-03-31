package domain.piece;

import domain.game.Turn;

public enum Team {

    CHO(true),
    HAN(false),
    NONE(false),
    ;

    private static final String NOT_SAME_TEAM = "본인의 진영의 기물이 아닙니다.";

    private final boolean isCho;

    Team(boolean isCho) {
        this.isCho = isCho;
    }

    public boolean isCho() {
        return isCho;
    }

    public void validateSameTeam(Turn turn) {
        if (this.isCho != turn.isCho()) {
            throw new IllegalArgumentException(NOT_SAME_TEAM);
        }
    }
}
