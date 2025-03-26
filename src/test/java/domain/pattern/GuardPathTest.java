package domain.pattern;

import static domain.Fixtures.EIGHT_FIVE;
import static domain.Fixtures.NINE_FIVE;
import static domain.Fixtures.NINE_FOUR;
import static domain.Fixtures.NINE_SIX;
import static domain.Fixtures.ZERO_FIVE;

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
    @MethodSource("provideGuardPath")
    void 사의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> guardPath = piece.findMovablePath(NINE_FIVE, afterPosition);

        // then
        Assertions.assertThat(guardPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideGuardPath() {
        Path pathOfGuard = new GuardPath();
        return Stream.of(
                Arguments.of(EIGHT_FIVE, pathOfGuard.getPatterns(Direction.UP)),
                Arguments.of(NINE_FOUR, pathOfGuard.getPatterns(Direction.LEFT)),
                Arguments.of(NINE_SIX, pathOfGuard.getPatterns(Direction.RIGHT)),
                Arguments.of(ZERO_FIVE, pathOfGuard.getPatterns(Direction.DOWN)));
    }

    @Test
    void 사의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(EIGHT_FIVE, NINE_SIX))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
