package janggi.piece;

import janggi.score.Score;

public enum Type {

    CANNON(new Score(7)),
    CHARIOT(new Score(13)),
    ELEPHANT(new Score(3)),
    GENERAL(new Score(0)),
    GUARD(new Score(3)),
    HORSE(new Score(5)),
    SOLDIER(new Score(2));

    private final Score score;

    Type(final Score score) {
        this.score = score;
    }

    public static Score getScore(final Type type) {
        return type.score;
    }
}
