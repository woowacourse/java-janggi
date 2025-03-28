package domain.piece;

import domain.score.Score;

public enum PieceType {

    WANG(new Score(0)),
    SA(new Score(3)),
    CHA(new Score(13)),
    SANG(new Score(3)),
    MA(new Score(5)),
    PO(new Score(7)),
    BYEONG(new Score(2)),
    ;

    private final Score score;

    PieceType(final Score score) {
        this.score = score;
    }

    public Score score() {
        return score;
    }
}
