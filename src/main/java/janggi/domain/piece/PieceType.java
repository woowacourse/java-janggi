package janggi.domain.piece;

public enum PieceType {
    CHA("차"),
    GUNG("궁"),
    JOLBYEOUNG("졸,병"),
    MA("마"),
    PO("포"),
    SA("사"),
    SANG("상"),
    EMPTY("없음");

    private final String displayName;

    PieceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
