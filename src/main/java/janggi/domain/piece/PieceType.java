package janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {
    GENERAL("궁"),
    GUARD("사"),
    ELEPHANT("상"),
    HORSE("마"),
    CANNON("포"),
    CHARIOT("차"),
    SOLDIER("병"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public static PieceType find(String pieceName) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(pieceName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 기물 이름입니다."));
    }

    public String getName() {
        return name;
    }
}
