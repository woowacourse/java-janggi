package janggiGame.piece.character;

public enum PieceType {
    ADVISOR(3),
    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    HORSE(5),
    KING(0),
    PAWN(2),
    EMPTY(0),
    ;

    private final int point;

    PieceType(final int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}
