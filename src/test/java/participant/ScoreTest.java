package participant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import pieces.PieceScore;
import pieces.PieceType;

class ScoreTest {

    @ParameterizedTest
    @EnumSource(value = PieceType.class, mode = Mode.EXCLUDE, names = "GUNG")
    void 기물에_따른_점수를_더한다(PieceType pieceType) {
        // given
        long pieceScore = PieceScore.from(pieceType).value();
        Score beforeScore = new Score(10L);
        // when
        Score afterScore = beforeScore.addScoreOf(pieceType);
        // then
        long expected = pieceScore + beforeScore.value();
        assertThat(afterScore.value()).isEqualTo(expected);
    }
}