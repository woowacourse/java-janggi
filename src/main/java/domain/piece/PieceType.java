package domain.piece;

public enum PieceType {
    BLANK("."),
    CANNON("C"),
    CHARIOT("R"),
    ELEPHANT("M"),
    GUARD("G"),
    HORSE("N"),
    KING("K"),
    PAWN("P");


    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
