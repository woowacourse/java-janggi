package pieces;

import java.util.Map;
import participant.Score;

public class PieceScore {

    private static final Map<PieceType, Score> SCORE_OF_PIECE_TYPE = Map.of(
        PieceType.CHA, new Score(13L),
        PieceType.PO, new Score(7L),
        PieceType.MA, new Score(5L),
        PieceType.SANG, new Score(3L),
        PieceType.SA, new Score(3L),
        PieceType.JOL_BYEONG, new Score(2L)
    );

    public static Score from(PieceType pieceType) {
        Score score = SCORE_OF_PIECE_TYPE.get(pieceType);
        if (score == null) {
            throw new IllegalArgumentException("해당 기물에 대한 점수가 존재하지 않습니다.");
        }
        return score;
    }
}
