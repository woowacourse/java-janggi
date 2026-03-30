package janggi.domain.piece;

public enum PieceType {
    CHA("차"),
    GUNG("궁"),
    JOL("졸"),
    MA("마"),
    PO("포"),
    SA("사"),
    SANG("상"),
    ;

    PieceType(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
