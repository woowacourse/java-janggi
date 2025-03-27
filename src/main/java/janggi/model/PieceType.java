package janggi.model;

import java.util.Arrays;

public enum PieceType {
    CANNON("포"),
    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("졸"),
    KING("궁"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PieceType from(String typeName) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 타입 입니다."));
    }
}
