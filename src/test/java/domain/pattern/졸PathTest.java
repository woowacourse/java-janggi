package domain.pattern;

import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._SEVEN_FIVE;
import static domain.Fixtures._SEVEN_FOUR;
import static domain.Fixtures._SEVEN_SIX;
import static domain.Fixtures._SIX_FIVE;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.졸병;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 졸PathTest {
    Piece piece = new 졸병(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide졸Path")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> 졸path = piece.findMovablePath(_SEVEN_FIVE, afterPosition);

        // when & then
        Assertions.assertThat(졸path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide졸Path() {
        Path pathOf졸 = new 졸Path();
        return Stream.of(
                Arguments.of(_SIX_FIVE, pathOf졸.getPatterns(Direction.UP)),
                Arguments.of(_SEVEN_FOUR, pathOf졸.getPatterns(Direction.LEFT)),
                Arguments.of(_SEVEN_SIX, pathOf졸.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_SEVEN_FIVE, _EIGHT_FIVE))
                .isInstanceOf(IllegalStateException.class);
    }
}
