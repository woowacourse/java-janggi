package piece;

import java.util.Arrays;

public enum PieceType {
    CHA("차"),
    SANG("상"),
    MA("마"),
    SA("사"),
    GUNG("궁"),
    FO("포"),
    JOL("졸");

    private static final String INVALID_TYPE = "존재하지 않는 타입입니다.";

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

    public String getType() {
        return type;
    }
}
