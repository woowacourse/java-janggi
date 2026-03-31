package janggi.domain.piece;

public enum PieceType {
    CHA("CH"),
    GUNG("GU"),
    MA("MA"),
    NONE("."),
    PAWN("JO"),
    PO("PO"),
    SA("SA"),
    SANG("SG");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
