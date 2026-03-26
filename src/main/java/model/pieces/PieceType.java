package model.pieces;

public enum PieceType {
    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    GENERAL("장"),
    CANNON("포"),
    SOLDIER("병");

    private final String symbol;

    PieceType(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
