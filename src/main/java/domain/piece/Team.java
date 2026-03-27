package domain.piece;

import domain.game.Turn;

import static util.ErrorMessage.NOT_SAME_TEAM;

public enum Team {

    CHO(true),
    HAN(false),
    NONE(false),
    ;

    private final boolean isCho;

    Team(boolean isCho) {
        this.isCho = isCho;
    }

    public boolean isCho() {
        return isCho;
    }

    public void validateSameTeam(Turn turn) {
        if (this.isCho != turn.isCho()) {
            throw new IllegalArgumentException(NOT_SAME_TEAM.getMessage());
        }
    }
}
