package domain;

public enum PieceType {
    KING("궁"),
    ROOK("차"),
    CANNON("포"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    PAWN("졸");

    private final String koreanName;

    PieceType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
