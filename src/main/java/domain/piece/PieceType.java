package domain.piece;

import java.util.Arrays;

public enum PieceType {

    CANNON("C", 7),
    CHARIOT("c", 13),
    ELEPHANT("E", 3),
    GENERAL("G", 0),
    GUARD("g", 3),
    HORSE("H", 5),
    SOLDIER("s", 2),
    ;

    private final String name;
    private final int score;

    PieceType(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public static PieceType getValue(String pieceType) {
        return Arrays.stream(values())
                .filter(type -> type.name().equals(pieceType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 종류의 기물입니다."));
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
