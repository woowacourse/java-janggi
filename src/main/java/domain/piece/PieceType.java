package domain.piece;

public enum PieceType {

    CANNON("C"),
    CHARIOT("c"),
    ELEPHANT("E"),
    GENERAL("G"),
    GUARD("g"),
    HORSE("H"),
    SOLDIER("s"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
