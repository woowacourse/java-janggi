package janggi.domain.piece;

import janggi.domain.Score;

public enum PieceType {
    CHARIOT(new Score(13)),
    CANNON(new Score(7)),
    HORSE(new Score(5)),
    ELEPHANT(new Score(3)),
    GUARD(new Score(3)),
    SOLDIER(new Score(2)),
    GENERAL(new Score(0)),
    ;

    private final Score score;

    PieceType(final Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}
