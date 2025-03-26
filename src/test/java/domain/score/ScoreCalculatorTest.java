package domain.score;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("점수 계산기 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScoreCalculatorTest {

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getPiecesAndExpected")
    void 모든_기물의_점수를_계산하여_반환한다(List<Piece> pieces, Score expected, String testName) {
        // given
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // when
        final Score actual = scoreCalculator.calculateTotalScoreOfPieces(pieces);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getPiecesAndExpected() {
        Team cho = Team.CHO;
        Team han = Team.HAN;

        return Stream.of(
                Arguments.of(List.of(new Byeong(cho), new Cha(cho)), new Score(15), "2+13=15점"),
                Arguments.of(List.of(new Sa(han), new Sa(han), new Ma(han)), new Score(11), "3+3+5=11점")
        );
    }
}