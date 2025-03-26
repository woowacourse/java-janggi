package piece.position;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiagonalGungsungPositionTest {

    private static Stream<Arguments> gungDiagonalPositions() {
        return Stream.of(
                Arguments.arguments(new JanggiPosition(0, 3)),
                Arguments.arguments(new JanggiPosition(0, 5)),
                Arguments.arguments(new JanggiPosition(2, 3)),
                Arguments.arguments(new JanggiPosition(2, 5)),
                Arguments.arguments(new JanggiPosition(7, 3)),
                Arguments.arguments(new JanggiPosition(7, 5)),
                Arguments.arguments(new JanggiPosition(9, 3)),
                Arguments.arguments(new JanggiPosition(9, 5)));
    }

    @ParameterizedTest
    @MethodSource("gungDiagonalPositions")
    void 대각선이동_가능한_궁위치인지_확인할_수_있다(JanggiPosition gungDiagonalPosition) {
        Assertions.assertThat(GungsungPosition.isPositionDiagonalGungPosition(gungDiagonalPosition)).isTrue();
    }

    @Test
    void 대각선이동_가능한_궁위치가_아니면_false를_반환한다() {
        JanggiPosition nonGungDiagonalPosition = new JanggiPosition(0, 0);
        Assertions.assertThat(GungsungPosition.isPositionDiagonalGungPosition(nonGungDiagonalPosition)).isFalse();
    }
}
