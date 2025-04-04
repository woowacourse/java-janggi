package domain.piece;

public enum PieceSymbol {
    CANNON("포"),
    CHARIOT("차"),
    ELEPHANT("상"),
    GENERAL("궁"),
    GUARD("사"),
    HORSE("마"),
    SOLDIER_JOL("졸"),
    SOLDIER_BYEONG("병"),
    EMPTY("ㅡ");

    private final String symbol;

    PieceSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static PieceSymbol fromSymbol(String symbol) {
        for (PieceSymbol value : values()) {
            if (value.symbol.equals(symbol)) {
                return value;
            }
        }
        return null;
    }
}
