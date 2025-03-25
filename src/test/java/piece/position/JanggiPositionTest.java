package piece.position;

import java.util.stream.Stream;
import move.direction.Direction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JanggiPositionTest {

    @Test
    void 포지션은_숫자를_가진다() {
        Assertions.assertThatNoException().isThrownBy(() -> new JanggiPosition(0, 0));
    }

    @Test
    void 포지션은_방향을_더할_수_있다() {
        JanggiPosition position = new JanggiPosition(0, 0);
        Direction direction = Direction.UP;

        JanggiPosition newPosition = position.add(direction);

        Assertions.assertThat(newPosition).isEqualTo(new JanggiPosition(1, 0));
    }

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
    void 포지션은_대각선이동_가능한_궁위치인지_확인할_수_있다(JanggiPosition gungDiagonalPosition) {
        Assertions.assertThat(gungDiagonalPosition.isPositionDiagonalGungPosition()).isTrue();
    }

    @Test
    void 포지션은_대각선이동_가능한_궁위치가_아니면_false를_반환한다() {
        JanggiPosition nonGungDiagonalPosition = new JanggiPosition(0, 0);
        Assertions.assertThat(nonGungDiagonalPosition.isPositionDiagonalGungPosition()).isFalse();
    }

    @Test
    void 포지션은_대각선을_판정할_수_있다() {
        JanggiPosition positionA = new JanggiPosition(0, 0);
        JanggiPosition positionB = new JanggiPosition(1, 1);
        JanggiPosition positionC = new JanggiPosition(1, 2);
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(positionA.isSameDiagonal(positionB)).isTrue(),
                () -> Assertions.assertThat(positionA.isSameDiagonal(positionC)).isFalse(),
                () -> Assertions.assertThat(positionB.isSameDiagonal(positionC)).isFalse()
        );
    }
}
