package piece;

import java.util.Arrays;

public enum PieceScore {

    CHA(PieceType.CHA, 13),
    FO(PieceType.FO, 7),
    MA(PieceType.MA, 5),
    SANG(PieceType.SANG, 3),
    SA(PieceType.SA, 3),
    JOL(PieceType.JOL, 2),
    GUNG(PieceType.GUNG, 0);

    private static final String INVALID_PIECE_SCORE = "지원하지 않는 기물 점수 입니다";

    private final PieceType pieceType;

    private final int point;

    PieceScore(PieceType pieceType, int point) {
        this.pieceType = pieceType;
        this.point = point;
    }

    public static PieceScore from(PieceType pieceType) {
        return Arrays.stream(PieceScore.values())
                .filter((pieceScore) -> pieceScore.pieceType.isSameType(pieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PIECE_SCORE));
    }

    public int getPoint() {
        return point;
    }
}
