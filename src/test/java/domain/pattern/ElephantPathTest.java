package domain.pattern;

import static domain.Fixtures._EIGHT_EIGHT;
import static domain.Fixtures._EIGHT_TWO;
import static domain.Fixtures._FOUR_EIGHT;
import static domain.Fixtures._FOUR_SIX;
import static domain.Fixtures._FOUR_TWO;
import static domain.Fixtures._NINE_SEVEN;
import static domain.Fixtures._NINE_THREE;
import static domain.Fixtures._SIX_FIVE;
import static domain.Fixtures._THREE_SEVEN;
import static domain.Fixtures._THREE_THREE;

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
    @MethodSource("provide상Path")
    void 상의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> elephantPath = piece.findMovablePath(_SIX_FIVE, afterPosition);

        // when & then
        Assertions.assertThat(elephantPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide상Path() {
        Path pathOfElephant = new ElephantPath();
        return Stream.of(
                Arguments.of(_FOUR_EIGHT, pathOfElephant.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(_EIGHT_EIGHT, pathOfElephant.getPatterns(Direction.RIGHT_DOWN)),
                Arguments.of(_NINE_SEVEN, pathOfElephant.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(_NINE_THREE, pathOfElephant.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(_EIGHT_TWO, pathOfElephant.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(_FOUR_TWO, pathOfElephant.getPatterns(Direction.LEFT_UP)),
                Arguments.of(_THREE_THREE, pathOfElephant.getPatterns(Direction.UP_LEFT)),
                Arguments.of(_THREE_SEVEN, pathOfElephant.getPatterns(Direction.UP_RIGHT))
        );
    }

    @Test
    void 상의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_SIX_FIVE, _FOUR_SIX))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
