package janggi.model.piece;

public enum PieceType {
    MA(5),
    SANG(3),
    JANG(13),
    SA(3),
    PHO(7),
    CHA(13),
    BYEONG(2);

    private final Score score;

    PieceType(Score score) {
        this.score = score;
    }

    PieceType(int scoreValue) {
        this(new Score(scoreValue));
    }

    public int getScore() {
        return score.value();
    }
}
