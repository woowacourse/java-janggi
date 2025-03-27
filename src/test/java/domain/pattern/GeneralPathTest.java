package domain.pattern;

import static domain.Fixtures.FIVE_EIGHT;
import static domain.Fixtures.FIVE_NINE;
import static domain.Fixtures.FIVE_ZERO;
import static domain.Fixtures.FOUR_NINE;
import static domain.Fixtures.SIX_NINE;

import domain.JanggiPosition;
import domain.piece.General;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GeneralPathTest {
    Piece piece = new General(Side.CHO);

    @ParameterizedTest
    @MethodSource("provideGeneralPath")
    void General의_이동_전_후_위치를_입력받으면_알맞은_경로를_찾을_수_있다(JanggiPosition afterPosition, List<Pattern> path) {
        // when
        List<Pattern> generalPath = piece.findMovablePath(FIVE_NINE, afterPosition);

        // then
        Assertions.assertThat(generalPath)
                .isEqualTo(path);
    }

    static Stream<Arguments> provideGeneralPath() {
        Path pathOfGeneral = new Path(Direction.createGeneralOrGuardPatternMap());
        return Stream.of(
                Arguments.of(FIVE_EIGHT, pathOfGeneral.getPatterns(Direction.UP)),
                Arguments.of(FOUR_NINE, pathOfGeneral.getPatterns(Direction.LEFT)),
                Arguments.of(SIX_NINE, pathOfGeneral.getPatterns(Direction.RIGHT)),
                Arguments.of(FIVE_ZERO, pathOfGeneral.getPatterns(Direction.DOWN)));
    }

    @Test
    void General의_이동_전_후_위치가_알맞지_않으면_예외를_발생시킨다() {
        // when & then
        Assertions.assertThatThrownBy(
                        () -> piece.findMovablePath(FIVE_EIGHT, SIX_NINE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
