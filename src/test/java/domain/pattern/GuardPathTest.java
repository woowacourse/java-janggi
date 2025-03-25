package domain.pattern;

import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._EIGHT_FOUR;
import static domain.Fixtures._NINE_FIVE;
import static domain.Fixtures._NINE_FOUR;
import static domain.Fixtures._NINE_SIX;
import static domain.Fixtures._ZERO_FIVE;

import domain.JanggiPosition;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GuardPathTest {
    Piece piece = new Guard(Side.CHO);

    @ParameterizedTest
    @MethodSource("provide사Path")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> GuardPath = piece.findMovablePath(_NINE_FIVE, afterPosition);

        // when & then
        Assertions.assertThat(GuardPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide사Path() {
        Path pathOfGuard = new GuardPath();
        return Stream.of(
                Arguments.of(_EIGHT_FIVE, pathOfGuard.getPatterns(Direction.UP)),
                Arguments.of(_NINE_FOUR, pathOfGuard.getPatterns(Direction.LEFT)),
                Arguments.of(_NINE_SIX, pathOfGuard.getPatterns(Direction.RIGHT)),
                Arguments.of(_ZERO_FIVE, pathOfGuard.getPatterns(Direction.DOWN)));
    }

    @Test
    void 사의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_NINE_FIVE, _EIGHT_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
