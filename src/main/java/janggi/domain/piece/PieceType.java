package janggi.domain.piece;

import janggi.domain.Score;
import java.util.Arrays;

public enum PieceType {
    CHARIOT(new Score(13)),
    CANNON(new Score(7)),
    HORSE(new Score(5)),
    ELEPHANT(new Score(3)),
    GUARD(new Score(3)),
    SOLDIER(new Score(2)),
    GENERAL(new Score(0)),
    ;

    private final Score score;

    PieceType(final Score score) {
        this.score = score;
    }

    public static PieceType convert(final String pieceTypeName) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.name().equals(pieceTypeName))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 기물명입니다: " + pieceTypeName));
    }

    public Score getScore() {
        return score;
    }
}
