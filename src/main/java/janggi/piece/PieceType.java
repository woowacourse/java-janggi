package janggi.piece;

public enum PieceType {
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    SCHOLAR(3),
    SOLDIER(2),
    GENERAL(0),
    NONE(0),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public double getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        if (this == PieceType.GENERAL) {
            return "K";
        }
        if (this == PieceType.SCHOLAR) {
            return "S";
        }
        if (this == PieceType.CHARIOT) {
            return "C";
        }
        if (this == PieceType.HORSE) {
            return "H";
        }
        if (this == PieceType.ELEPHANT) {
            return "E";
        }
        if (this == PieceType.CANNON) {
            return "B";
        }
        if (this == PieceType.SOLDIER) {
            return "J";
        }
        return "N";
    }
}
