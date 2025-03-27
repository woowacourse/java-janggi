package domain.pattern;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_FOUR;
import static domain.Fixtures.FOUR_FOUR;
import static domain.Fixtures.FOUR_SIX;
import static domain.Fixtures.SIX_FIVE;
import static domain.Fixtures.SIX_SEVEN;
import static domain.Fixtures.THREE_EIGHT;
import static domain.Fixtures.THREE_FOUR;
import static domain.Fixtures.TWO_FIVE;
import static domain.Fixtures.TWO_SEVEN;

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
        List<Pattern> horsePath = piece.findMovablePath(FOUR_SIX, afterPosition);

        // then
        Assertions.assertThat(horsePath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideHorsePath() {
        Path pathOfHorse = new Path(Direction.createHorsePatternMap());
        return Stream.of(
                Arguments.of(TWO_FIVE, pathOfHorse.getPatterns(Direction.LEFT_UP)),
                Arguments.of(SIX_FIVE, pathOfHorse.getPatterns(Direction.RIGHT_UP)),
                Arguments.of(FIVE_FOUR, pathOfHorse.getPatterns(Direction.UP_RIGHT)),
                Arguments.of(THREE_FOUR, pathOfHorse.getPatterns(Direction.UP_LEFT)),
                Arguments.of(TWO_SEVEN, pathOfHorse.getPatterns(Direction.LEFT_DOWN)),
                Arguments.of(THREE_EIGHT, pathOfHorse.getPatterns(Direction.DOWN_LEFT)),
                Arguments.of(FIVE_EIGHT, pathOfHorse.getPatterns(Direction.DOWN_RIGHT)),
                Arguments.of(SIX_SEVEN, pathOfHorse.getPatterns(Direction.RIGHT_DOWN))
        );
    }

    @Test
    void Horse의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(() -> piece.findMovablePath(FOUR_SIX, FOUR_FOUR))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
