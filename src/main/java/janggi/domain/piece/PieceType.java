package janggi.domain.piece;

public enum PieceType {
    CANNON("포", 7),
    CHARIOT("차", 13),
    ELEPHANT("상", 3),
    GENERAL("궁", 0),
    GUARD("사", 3),
    HORSE("마", 5),
    SOLDIER("졸", 2),
    NONE("ㅁ", 0);

    private final String name;
    private final int score;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
