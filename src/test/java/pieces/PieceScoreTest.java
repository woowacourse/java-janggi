package pieces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import participant.Score;

class PieceScoreTest {

    private static final Map<PieceType, Score> SCORE_OF_PIECE_TYPE = Map.of(
        PieceType.CHA, new Score(13L),
        PieceType.PO, new Score(7L),
        PieceType.MA, new Score(5L),
        PieceType.SANG, new Score(3L),
        PieceType.SA, new Score(3L),
        PieceType.JOL_BYEONG, new Score(2L)
    );

    @ParameterizedTest
    @EnumSource(value = PieceType.class, mode = Mode.EXCLUDE, names = "GUNG")
    void 기물_종류에_따른_점수를_반환한다(PieceType pieceType) {
        // when
        Score score = PieceScore.from(pieceType);
        // then
        Score expected = SCORE_OF_PIECE_TYPE.get(pieceType);
        assertThat(score).isEqualTo(expected);
    }
}