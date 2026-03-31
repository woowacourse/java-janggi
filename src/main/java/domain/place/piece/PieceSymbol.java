package domain.place.piece;

public enum PieceSymbol {
    GENERAL("궁", 0),
    CHARIOT("차", 13),
    CANNON("포", 7),
    HORSE("마", 5),
    ELEPHANT("상", 3),
    GUARD("사", 2),
    SOLDIER("졸", 1);

    private final String display;
    private final int score;

    PieceSymbol(String display, int score) {
        this.display = display;
        this.score = score;
    }

    public String display() {
        return display;
    }

    public int getScore() {
        return score;
    }

}
