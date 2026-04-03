package domain.piece;

import java.util.Arrays;

public enum PieceType {
    SOLDIER("soldier", 2d),
    GUARD("guard", 3d),
    ELEPHANT("elephant", 3d),
    HORSE("horse", 5d),
    CANNON("cannon", 7d),
    CHARIOT("chariot", 13d),
    GENERAL("general", 0d),
    ;

    private static final String NOT_FOUND_PIECE_TYPE = "[ERROR] 존재하지 않는 기물입니다.";

    private final String name;
    private final double score;

    PieceType(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public PieceType from(String name) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PIECE_TYPE));
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }
}
