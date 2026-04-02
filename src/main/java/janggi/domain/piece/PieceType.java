package janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {
    CHA("CH", 13),
    GUNG("GU", 0),
    MA("MA", 5),
    NONE(".", 0),
    PAWN("JO", 2),
    PO("PO", 7),
    SA("SA", 3),
    SANG("SG", 3);

    private static final String INVALID_PIECE_TYPE_NAME = "해당 이름의 기물 종류가 없습니다.";

    private final String name;
    private final int score;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public static PieceType from(String name) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PIECE_TYPE_NAME));
    }
}
