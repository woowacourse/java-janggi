package domain.pieces;

import domain.game.Score;

public enum PieceType {
    CHA(13),
    PO(7),
    MA(5),
    SANG(3),
    SA(3),
    JOL_BYEONG(2),
    GUNG(0),
    EMPTY(0);

    private final double point;

    PieceType(double point) {
        this.point = point;
    }

    public Score score() {
        return new Score(point);
    }
}
