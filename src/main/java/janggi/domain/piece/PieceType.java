package janggi.domain.piece;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, PieceType> BY_CODE = new HashMap<>();

    static {
        for (PieceType pieceType : values()) {
            BY_CODE.put(pieceType.code, pieceType);
        }
    }

    PieceType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PieceType fromCode(String code) {
        if (!BY_CODE.containsKey(code)) {
            throw new IllegalArgumentException("알 수 없는 기물 코드입니다: " + code);
        }

        return BY_CODE.get(code);
    }
}
