package model.piece;

public enum Score {
    JANG(0),
    SA(3),
    SANG(3),
    MA(5),
    CHA(13),
    PO(7),
    BYEONG(2);

    private int score;

    Score(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
