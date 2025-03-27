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

    public static boolean isJanggun(final PieceType pieceType) {
        return JANGGUN.equals(pieceType);
    }

    public static boolean isPo(final PieceType pieceType) {
        return PO.equals(pieceType);
    }

    public String getValue() {
        return name;
    }
}
