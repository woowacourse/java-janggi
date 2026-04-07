package janggi.domain.piece;

import janggi.domain.Score;

public enum PieceType {
    CHARIOT(new Score(13.0)),
    CANNON(new Score(7.0)),
    HORSE(new Score(5.0)),
    ELEPHANT(new Score(3.0)),
    GUARD(new Score(3.0)),
    SOLDIER(new Score(2.0)),
    GENERAL(new Score(0.0));

    private final Score score;

    PieceType(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}
