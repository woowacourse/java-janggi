package janggiGame.piece;

public enum Type {
    KING(100),
    ADVISOR(3),
    PAWN(2),
    ELEPHANT(3),
    HORSE(5),
    CANNON(7),
    CHARIOT(13);

    private final Integer point;

    Type(Integer point) {
        this.point = point;
    }

    public Integer getPoint() {
        return point;
    }
}
