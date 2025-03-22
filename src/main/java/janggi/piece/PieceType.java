package janggi.piece;

public enum PieceType {
    KING("G"),
    GUARD("S"),
    HORSE("M"),
    ELEPHANT("E"),
    CANNON("P"),
    CHARIOT("C"),
    SOLDIER("J"),
    EMPTY("·");

    final String symbol;

    PieceType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
