package domain.piece;

public enum PieceType {
    BYEONG("병"),
    CHA("차"),
    GUNG("궁"),
    MA("마"),
    PHO("포"),
    SA("사"),
    SANG("상");

    private String type;

    PieceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
