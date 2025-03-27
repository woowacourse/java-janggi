package domain.pattern;

import static domain.Fixtures.EIGHT_EIGHT;
import static domain.Fixtures.EIGHT_FOUR;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.SEVEN_NINE;
import static domain.Fixtures.SEVEN_THREE;
import static domain.Fixtures.SIX_FOUR;
import static domain.Fixtures.THREE_NINE;
import static domain.Fixtures.THREE_THREE;
import static domain.Fixtures.TWO_EIGHT;
import static domain.Fixtures.TWO_FOUR;

import domain.JanggiPosition;
import domain.piece.Elephant;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ElephantPathTest {
    Piece piece = new Elephant(Side.CHO);

    @ParameterizedTest
    @MethodSource("provideElephantPath")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> elephantPath = piece.findMovablePath(FIVE_SIX, afterPosition);

        // then
        Assertions.assertThat(elephantPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideElephantPath() {
        Path pathOfElephant = new Path(Direction.createElephantPatternMap());
        return Stream.of(
                Arguments.of(EIGHT_FOUR, pathOfElephant.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(EIGHT_EIGHT, pathOfElephant.getPatterns(Direction.RIGHT_DOWN)),
                Arguments.of(SEVEN_NINE, pathOfElephant.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(THREE_NINE, pathOfElephant.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(TWO_EIGHT, pathOfElephant.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(TWO_FOUR, pathOfElephant.getPatterns(Direction.LEFT_UP)),
                Arguments.of(THREE_THREE, pathOfElephant.getPatterns(Direction.UP_LEFT)),
                Arguments.of(SEVEN_THREE, pathOfElephant.getPatterns(Direction.UP_RIGHT))
        );
    }

    @Test
    void 상의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(FIVE_SIX, SIX_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
