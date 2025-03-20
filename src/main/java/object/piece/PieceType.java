package object.piece;

import java.util.Arrays;

public enum PieceType {
    CHARIOT("차"),
    ELEPHANT("상"),
    HORSE("마"),
    GUARD("사"),
    GENERAL("궁"),
    CANNON("포"),
    SOLIDER("졸"),
    ;

    public static final String INVALID_TYPE = "존재하지 않는 타입입니다.";

    private final String type;

    PieceType(String type) {
        this.type = type;
    }

    public static PieceType from(String s) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> s.equals(pieceType.type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TYPE));
    }
}
