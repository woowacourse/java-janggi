package domain;

import java.util.Arrays;

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

    public static PieceType getPieceType(String koreanName) {
        return Arrays.stream(PieceType.values())
                .filter(type -> type.koreanName.equals(koreanName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 타입입니다: " + koreanName));
    }
}
