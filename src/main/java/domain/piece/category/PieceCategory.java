package domain.piece.category;

import domain.piece.Score;

public enum PieceCategory {
    CANNON(new Score(7)),
    CHARIOT(new Score(13)),
    ELEPHANT(new Score(3)),
    GUARD(new Score(3)),
    HORSE(new Score(5)),
    KING(new Score(0)),
    SOLDIER(new Score(2)),
    NONE(new Score(0));

    private final Score score;

    PieceCategory(final Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}
