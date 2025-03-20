package domain.piece;

public enum PieceType {
    BYEONG("병"),
    CHA("차"),
    GUNG("궁"),
    MA("마"),
    PHO("포"),
    SA("사"),
    SANG("상");

    private final String description;

    PieceType(String type) {
        this.description = type;
    }

    public String getDescription() {
        return description;
    }
}
