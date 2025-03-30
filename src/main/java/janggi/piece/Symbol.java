package janggi.piece;

public enum Symbol {

    SOLDIER("J", 2),
    GUARD("S", 3),
    ELEPHANT("E", 3),
    HORSE("M", 5),
    CANNON("P", 7),
    CHARIOT("C", 13),
    KING("G", 0),
    EMPTY("·", 0),
    ;

    private final String pieceSymbol;
    private final int score;

    Symbol(final String symbol, final int score) {
        this.pieceSymbol = symbol;
        this.score = score;
    }

    public String getSymbol() {
        return pieceSymbol;
    }

    public int getScore() {
        return score;
    }

}
