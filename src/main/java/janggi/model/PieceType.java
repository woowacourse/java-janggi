package janggi.model;

import java.util.Arrays;

public enum PieceType {
    CANNON("포", 7),
    CHARIOT("차", 13),
    HORSE("마", 5),
    ELEPHANT("상", 3),
    GUARD("사", 3),
    SOLDIER("졸", 2),
    KING("궁", 0),
    ;

    private final String name;
    private final int score;

    PieceType(String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int score() {
        return score;
    }

    public static PieceType from(String typeName) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 타입 입니다."));
    }
}
