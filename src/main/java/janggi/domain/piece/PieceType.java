package janggi.domain.piece;

public enum PieceType {
    KING("G", 0),
    GUARD("S", 3),
    HORSE("M", 5),
    ELEPHANT("E", 3),
    CANNON("P", 7),
    CHARIOT("C", 13),
    SOLDIER("J", 2),
    EMPTY("·", 0);

    private final String symbol;
    private final int score;

    PieceType(final String symbol, final int score) {
        this.symbol = symbol;
        this.score = score;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getScore() { return score; }
}
