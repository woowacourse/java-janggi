package domain.piece;

public enum PieceType {
    CANNON("C"),
    CHARIOT("R"),
    ELEPHANT("E"),
    GUARD("G"),
    HORSE("H"),
    KING("K"),
    SOLDIER("S");

    private final String description;

    PieceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
