package domain.pattern;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_SEVEN;
import static domain.Fixtures.FIVE_SIX;
import static domain.Fixtures.FOUR_SEVEN;
import static domain.Fixtures.SIX_SEVEN;

import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.Side;
import domain.piece.Soldier;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierJolPathTest {
    Piece piece = new Soldier(Side.CHO);

    @ParameterizedTest
    @MethodSource("provideJolPath")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> jolPath = piece.findMovablePath(FIVE_SEVEN, afterPosition);

        // then
        Assertions.assertThat(jolPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideJolPath() {
        Path pathOfJol = new Path(Direction.createJolPatternMap());
        return Stream.of(
                Arguments.of(FIVE_SIX, pathOfJol.getPatterns(Direction.UP)),
                Arguments.of(FOUR_SEVEN, pathOfJol.getPatterns(Direction.LEFT)),
                Arguments.of(SIX_SEVEN, pathOfJol.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(FIVE_SEVEN, FIVE_EIGHT))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
