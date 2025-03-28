package janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {
    GENERAL("궁", 0),
    GUARD("사", 3),
    ELEPHANT("상", 3),
    HORSE("마", 5),
    CANNON("포", 7),
    CHARIOT("차", 13),
    SOLDIER("병", 2),
    ;

    private final String name;
    private final int score;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
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

    public int getScore() {
        return score;
    }
}
