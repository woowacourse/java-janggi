package domain;

public enum PieceType {

    GENERAL("궁", 0),
    GUARD("사", 3),
    SOLDIER("졸", 2),

    HORSE("마", 5),
    ELEPHANT("상", 3),
    CHARIOT("차", 13),

    CANNON("포", 7),

    EMPTY_VALUE("*", 0)
    ;

    private final String description;
    private final int score;

    PieceType(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public String description() {
        return this.description;
    }

    public int score() {
        return score;
    }
}
