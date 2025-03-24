package domain.type;

import domain.score.Score;

public enum ChessPieceType {

    CHARIOT(new Score(13)),
    HORSE(new Score(5)),
    ELEPHANT(new Score(3)),
    CANNON(new Score(7)),
    GUARD(new Score(3)),
    PAWN(new Score(2)),
    KING(new Score(0))
    ;

    public final Score score;

    ChessPieceType(Score score) {
        this.score = score;
    }
}
