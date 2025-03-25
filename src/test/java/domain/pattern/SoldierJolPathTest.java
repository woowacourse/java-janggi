package domain.pattern;

import static domain.Fixtures._EIGHT_FIVE;
import static domain.Fixtures._SEVEN_FIVE;
import static domain.Fixtures._SEVEN_FOUR;
import static domain.Fixtures._SEVEN_SIX;
import static domain.Fixtures._SIX_FIVE;

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
    @MethodSource("provide졸Path")
    void 졸의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> jolpath = piece.findMovablePath(_SEVEN_FIVE, afterPosition);

        // when & then
        Assertions.assertThat(jolpath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provide졸Path() {
        Path pathOfJol = new SoldierJolPath();
        return Stream.of(
                Arguments.of(_SIX_FIVE, pathOfJol.getPatterns(Direction.UP)),
                Arguments.of(_SEVEN_FOUR, pathOfJol.getPatterns(Direction.LEFT)),
                Arguments.of(_SEVEN_SIX, pathOfJol.getPatterns(Direction.RIGHT))
        );
    }

    @Test
    void 졸의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_SEVEN_FIVE, _EIGHT_FIVE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
