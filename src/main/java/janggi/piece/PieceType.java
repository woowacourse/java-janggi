package janggi.piece;

public enum PieceType {
    BYEONG("병"),
    CHA("차"),
    GUNG("궁"),
    MA("마"),
    PO("포"),
    SA("사"),
    SANG("상"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
