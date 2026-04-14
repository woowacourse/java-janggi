package janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {
    SOLDIER("SOL"),
    ADVISOR("ADV"),
    CANNON("CAN"),
    ELEPHANT("ELE"),
    HORSE("HOR"),
    KING("KIN"),
    TANK("TAN"),
    EMPTY("EMP");

    private final String code;

    PieceType(String code) {
        this.code = code;
    }

    public static PieceType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 기물 코드입니다: " + code));
    }

    public String getCode() {
        return code;
    }
}
