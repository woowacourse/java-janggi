package domain.piece;

import domain.game.Turn;

import static util.ErrorMessage.NOT_SAME_TEAM;

public enum Team {

    CHO,
    HAN,
    NONE,
    ;

    public void validateSameTeam(Turn turn) {
        if (!this.name().equals(turn.name())) {
            throw new IllegalArgumentException(NOT_SAME_TEAM.getMessage());
        }
    }
}
