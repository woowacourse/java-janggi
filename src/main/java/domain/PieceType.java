package domain;

public enum PieceType {
    KING("궁", 0),
    ROOK("차", 13),
    CANNON("포", 7),
    HORSE("마", 5),
    ELEPHANT("상", 3),
    GUARD("사", 3),
    PAWN("졸", 2);

    private final String koreanName;
    private final int score;

    PieceType(String koreanName, int score) {
        this.koreanName = koreanName;
        this.score = score;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public int getScore() {
        return score;
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
