package domain.piece;

public enum PieceType {
    CHA("CH"),
    MA("MA"),
    SA("SA"),
    SANG("SD"),
    JANG("JA"),
    PO("PO"),
    JOL("ZO"),
    NONE("  ");

    private final String symbol;

    PieceType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
