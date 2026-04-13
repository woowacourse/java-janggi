package domain.piece;

import java.util.Arrays;

public enum PieceScore {

    CHARIOT_SCORE(PieceType.CHARIOT, 13),
    CANNON_SCORE(PieceType.CANNON, 7),
    HORSE_SCORE(PieceType.HORSE, 5),
    ELEPHANT_SCORE(PieceType.ELEPHANT, 3),
    GUARD_SCORE(PieceType.GUARD, 3),
    SOLDIER_SCORE(PieceType.SOLDIER, 2),
    NONE_SCORE(PieceType.NONE, 0),
    ;

    private final PieceType pieceType;
    private final int score;

    PieceScore(PieceType pieceType, int score) {
        this.pieceType = pieceType;
        this.score = score;
    }

    public static int getScore(Piece piece) {
        return Arrays.stream(PieceScore.values())
                .filter(pieceScore -> piece.pieceType() == pieceScore.pieceType)
                .findFirst()
                .orElse(NONE_SCORE)
                .getScore();
    }

    public int getScore() {
        return score;
    }

}
