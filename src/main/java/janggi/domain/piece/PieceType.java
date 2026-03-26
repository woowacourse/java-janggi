package janggi.domain.piece;

public enum PieceType {
    CHA("차"),
    GUNG("궁"),
    MA("마"),
    NONE("."),
    PAWN("졸"),
    PO("포"),
    SA("사"),
    SANG("상");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
