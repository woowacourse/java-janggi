package model.piece;

public enum PieceInfo {
    JANG("漢", 0),
    SA("士", 3),
    SANG("象", 3),
    MA("馬", 5),
    CHA("車", 13),
    PHO("包", 7),
    BYEONG("兵", 2);

    private final String name;
    private final double score;

    PieceInfo(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }
}
