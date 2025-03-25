package piece;

public enum PieceType {
    GREEN_CANNON("n", 1),
    GREEN_CHARIOT("c", 1),
    GREEN_ELEPHANT("e", 1),
    GREEN_GENERAL("g", 1),
    GREEN_GUARD("r", 1),
    GREEN_HORSE("h", 1),
    GREEN_SOLDIER("s", 1),

    RED_CANNON("N", -1),
    RED_CHARIOT("C", -1),
    RED_ELEPHANT("E", -1),
    RED_GENERAL("G", -1),
    RED_GUARD("R", -1),
    RED_HORSE("H", -1),
    RED_SOLDIER("S", -1);

    private final String expression;
    private final int side;

    PieceType(String expression, int side) {
        this.expression = expression;
        this.side = side;
    }

    public String getExpression() {
        return expression;
    }

    public int getSide() {
        return side;
    }

    public boolean isCannon() {
        return this == RED_CANNON || this == GREEN_CANNON ;
    }

    public boolean isNotCannon() {
        return this != RED_CANNON && this != GREEN_CANNON ;
    }
}
