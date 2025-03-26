package janggi.piece;

public enum PieceType {

    BYEONG("병"),
    CHA("차"),
    JANGGUN("왕"),
    JOL("졸"),
    MA("마"),
    PO("포"),
    SA("사"),
    SANG("상"),
    ;

    private final String name;

    PieceType(final String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
