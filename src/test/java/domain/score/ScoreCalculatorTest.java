package domain.score;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.Wang;
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
                Arguments.of(List.of(new Wang(cho)), new Score(0), "왕은 0점"),
                Arguments.of(List.of(new Sa(cho)), new Score(3), "사는 3점"),
                Arguments.of(List.of(new Cha(cho)), new Score(13), "차는 13점"),
                Arguments.of(List.of(new Sang(cho)), new Score(3), "상은 3점"),
                Arguments.of(List.of(new Ma(cho)), new Score(5), "마는 5점"),
                Arguments.of(List.of(new Po(cho)), new Score(7), "포는 7점"),
                Arguments.of(List.of(new Byeong(cho)), new Score(2), "병은 2점"),

                Arguments.of(List.of(), new Score(0), "빈 리스트는 0점"),

                Arguments.of(List.of(new Sa(cho), new Sa(cho)), new Score(6), "사 두 개는 6점"),
                Arguments.of(List.of(new Cha(cho), new Cha(cho)), new Score(26), "차 두 개는 26점"),
                Arguments.of(List.of(new Sang(cho), new Sang(cho)), new Score(6), "상 두 개는 6점"),
                Arguments.of(List.of(new Ma(cho), new Ma(cho)), new Score(10), "마 두 개는 10점"),
                Arguments.of(List.of(new Po(cho), new Po(cho)), new Score(14), "포 두 개는 14점"),
                Arguments.of(List.of(new Byeong(cho), new Byeong(cho)), new Score(4), "병 두 개는 4점"),

                Arguments.of(List.of(new Byeong(cho), new Cha(cho)), new Score(15), "병 2점 + 차 13점 = 15점"),
                Arguments.of(List.of(new Sa(han), new Sa(han), new Ma(han)), new Score(11), "사 두 개 6점 + 마 5점 = 11점"),

                Arguments.of(List.of(new Wang(han), new Sa(han), new Cha(han), new Sang(han), new Ma(han), new Po(han), new Byeong(han)),
                        new Score(33), "전체 기물 총 33점")
        );
    }
}