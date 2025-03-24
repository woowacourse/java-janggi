package domain.pattern;

import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._EIGHT_THREE;
import static domain.Fixtures._FIVE_SIX;
import static domain.Fixtures._FIVE_TWO;
import static domain.Fixtures._FOUR_FIVE;
import static domain.Fixtures._FOUR_FOUR;
import static domain.Fixtures._FOUR_THREE;
import static domain.Fixtures._SEVEN_SIX;
import static domain.Fixtures._SEVEN_TWO;
import static domain.Fixtures._SIX_FOUR;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.마;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 마PathTest {
    Piece piece = new 마(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide마Path")
    void 마의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> 마path = piece.findMovablePath(_SIX_FOUR, afterPosition);

        // when & then
        Assertions.assertThat(마path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide마Path() {
        마Path pathOf마 = new 마Path();
        return Stream.of(
                Arguments.of(_FIVE_TWO, pathOf마.getPatterns(Direction.LEFT_UP)),
                Arguments.of(_FIVE_SIX, pathOf마.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(_FOUR_FIVE, pathOf마.getPatterns(Direction.UP_RIGHT)),
                Arguments.of(_FOUR_THREE, pathOf마.getPatterns(Direction.UP_LEFT)),
                Arguments.of(_SEVEN_TWO, pathOf마.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(_EIGHT_THREE, pathOf마.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(_EIGHT_FIVE, pathOf마.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(_SEVEN_SIX, pathOf마.getPatterns(Direction.RIGHT_DOWN))
        );
    }

    @Test
    void 마의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_SIX_FOUR, _FOUR_FOUR))
                .isInstanceOf(IllegalStateException.class);
    }
}
