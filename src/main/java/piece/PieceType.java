package piece;

public enum PieceType {
    CHA("차"),
    SANG("상"),
    MA("마"),
    SA("사"),
    GUNG("궁"),
    FO("포"),
    JOL("졸");

    private final String type;

    PieceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean isSameType(PieceType pieceType) {
        return type.equals(pieceType.type);
    }
}
