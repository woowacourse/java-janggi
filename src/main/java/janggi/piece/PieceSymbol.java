package janggi.piece;

public enum PieceSymbol {

    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    GENERAL(0),
    GUARD(3),
    HORSE(5),
    SOLDIER_JOL(2),
    SOLDIER_BYEONG(2),
    ;

    private final int point;

    PieceSymbol(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}
