package domain.pattern;

import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._EIGHT_FOUR;
import static domain.Fixtures._NINE_FIVE;
import static domain.Fixtures._NINE_FOUR;
import static domain.Fixtures._NINE_SIX;
import static domain.Fixtures._ZERO_FIVE;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.궁;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class 궁PathTest {
    Piece piece = new 궁(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide궁Path")
    void 궁의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPostion, List<Pattern> path) {
        // when
        List<Pattern> 궁path = piece.findMovablePath(_NINE_FIVE, afterPostion);

        // when & then
        Assertions.assertThat(궁path)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide궁Path() {
        Path pathOf궁 = new 궁Path();
        return Stream.of(
                Arguments.of(_EIGHT_FIVE, pathOf궁.getPatterns(Direction.UP)),
                Arguments.of(_NINE_FOUR, pathOf궁.getPatterns(Direction.LEFT)),
                Arguments.of(_NINE_SIX, pathOf궁.getPatterns(Direction.RIGHT)),
                Arguments.of(_ZERO_FIVE, pathOf궁.getPatterns(Direction.DOWN)));
    }

    @Test
    void 궁의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(
                        () -> piece.findMovablePath(_NINE_FIVE, _EIGHT_FOUR))
                .isInstanceOf(IllegalStateException.class);
    }
}
