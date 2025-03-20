package janggi.piece;

public enum PieceType {
    GUNG("궁"),
    SA("사"),
    CHA("차"),
    PO("포"),
    MA("마"),
    SANG("상"),
    JOL("졸"),
    ;
    private final String name;

    PieceType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
