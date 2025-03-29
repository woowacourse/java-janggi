package piece;

public enum PieceType {

    KING("궁", 0),
    CHARIOT("차", 13),
    CANNON("포", 7),
    HORSE("마", 5),
    ELEPHANT("상", 3),
    GUARD("사", 3),
    SOLDIER("졸", 2),
    ;

    private final String name;
    private final int score;

    PieceType(final String name, final int score) {
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
