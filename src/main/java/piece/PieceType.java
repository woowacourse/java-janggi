package piece;

import java.util.Arrays;

public enum PieceType {
    CANNON(1, 7),
    CHARIOT(2, 13),
    ELEPHANT(3, 3),
    GENERAL(4, 0),
    SOLDIER(5, 2),
    GUARD(6, 3),
    HORSE(7, 5);

    private final int id;
    private final int score;

    PieceType(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public static PieceType findById(int id) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] id에 해당하는 기물 타입이 없습니다."));
    }

    public static boolean isCannon(Piece piece) {
        return piece.getPieceType() == CANNON;
    }

    public static boolean isNotCannon(Piece piece) {
        return piece.getPieceType() != CANNON;
    }

    public static boolean isGeneral(Piece piece) {
        return piece.getPieceType() == GENERAL;
    }
}
