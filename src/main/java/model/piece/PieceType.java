package model.piece;

public enum PieceType {
    PALACE(0),
    CHARIOT(13),
    PAO(7),
    HORSE(5),
    ELEPHANT(3),
    SOLDIER(3),
    PAWN(2),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
