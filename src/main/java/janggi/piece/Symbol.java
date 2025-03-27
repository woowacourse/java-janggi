package janggi.piece;

public enum Symbol {

    SOLDIER("J"),
    GUARD("S"),
    ELEPHANT("E"),
    HORSE("M"),
    CANNON("P"),
    CHARIOT("C"),
    KING("G"),
    EMPTY("·"),
    ;

    private final String pieceSymbol;

    Symbol(final String symbol) {
        this.pieceSymbol = symbol;
    }

    public String getSymbol() {
        return pieceSymbol;
    }

}
