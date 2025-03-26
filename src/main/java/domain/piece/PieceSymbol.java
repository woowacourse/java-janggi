package domain.piece;

public enum PieceSymbol {
    CANNON("P"),
    CHARIOT("C"),
    ELEPHANT("E"),
    GENERAL("K"),
    GUARD("S"),
    HORSE("H"),
    SOLDIER_JOL("J"),
    SOLDIER_BYEONG("B"),
    EMPTY("_");

    private final String symbol;

    PieceSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
