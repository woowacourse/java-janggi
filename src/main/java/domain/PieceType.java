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

    public static PieceType fromKoreanName(String koreanName) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.koreanName.equals(koreanName)) {
                return pieceType;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 기물입니다.");
    }
}
