package janggi.domain.piece;

public enum PieceType {
    CHA("차"),
    PHO("포"),
    MA("마"),
    SANG("상"),
    JANG("장"),
    SA("사"),
    JOL("졸");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }
}
