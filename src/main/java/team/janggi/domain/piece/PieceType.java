package team.janggi.domain.piece;

import java.util.Arrays;

public enum PieceType {
    KING(0),
    GUARD(3),
    HORSE(5),
    ELEPHANT(3),
    CHARIOT(13),
    CANNON(7),
    SOLDIER(2),
    EMPTY(0);

    PieceType(int pieceScore) {
        this.pieceScore = pieceScore;
    }

    final int pieceScore;

    public int getPieceScore() {
        return pieceScore;
    }

    public static PieceType from(String pieceType) {
        return Arrays.stream(PieceType.values())
                .filter(type -> type.name().equals(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 기물 이름입니다: " + pieceType));
    }
}
