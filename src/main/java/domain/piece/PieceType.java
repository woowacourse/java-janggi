package domain.piece;

public enum PieceType {
    BYEONG("병", 2),
    CHA("차", 13),
    GUNG("궁", 0),
    MA("마", 5),
    PHO("포", 7),
    SA("사", 3),
    SANG("상", 3);

    private final String pieceName;
    private final int score;

    PieceType(String pieceName, int score) {
        this.pieceName = pieceName;
        this.score = score;
    }

    public String getPieceName() {
        return pieceName;
    }

    public int getScore() {
        return score;
    }
}
