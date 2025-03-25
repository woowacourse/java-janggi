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
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class HorsePathTest {
    Piece piece = new Horse(Side.CHO);

    @ParameterizedTest
    @MethodSource("provideHorsePath")
    void Horse의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> horsePath = piece.findMovablePath(_SIX_FOUR, afterPosition);

        // when & then
        Assertions.assertThat(horsePath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideHorsePath() {
        HorsePath pathOfHorse = new HorsePath();
        return Stream.of(
                Arguments.of(_FIVE_TWO, pathOfHorse.getPatterns(Direction.LEFT_UP)),
                Arguments.of(_FIVE_SIX, pathOfHorse.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(_FOUR_FIVE, pathOfHorse.getPatterns(Direction.UP_RIGHT)),
                Arguments.of(_FOUR_THREE, pathOfHorse.getPatterns(Direction.UP_LEFT)),
                Arguments.of(_SEVEN_TWO, pathOfHorse.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(_EIGHT_THREE, pathOfHorse.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(_EIGHT_FIVE, pathOfHorse.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(_SEVEN_SIX, pathOfHorse.getPatterns(Direction.RIGHT_DOWN))
        );
    }

    @Test
    void Horse의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(_SIX_FOUR, _FOUR_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
