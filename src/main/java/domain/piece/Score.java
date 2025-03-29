package domain.piece;

import java.util.Map;

public class Score {

    private static final Map<PieceType, Integer> scores = Map.of(
            PieceType.BYEONG, 2,
            PieceType.CHA, 13,
            PieceType.GUNG, 0,
            PieceType.MA, 5,
            PieceType.PHO, 7,
            PieceType.SA, 3,
            PieceType.SANG, 3
    );

    public static int getScore(PieceType pieceType) {
        return scores.get(pieceType);
    }

}
