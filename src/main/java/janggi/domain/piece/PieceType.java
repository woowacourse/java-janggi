package janggi.domain.piece;

public enum PieceType {
    GENERAL("將", new Score(0)),
    CHARIOT("車", new Score(13)),
    CANNON("包", new Score(7)),
    HORSE("馬", new Score(5)),
    ELEPHANT("象", new Score(3)),
    GUARD("士", new Score(3)),
    SOLDIER("兵", new Score(2));

    private final String type;
    private final Score score;

    PieceType(String type, Score score) {
        this.type = type;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public Score getScore() {
        return score;
    }
}
