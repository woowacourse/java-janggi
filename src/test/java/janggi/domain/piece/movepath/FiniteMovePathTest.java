package janggi.domain.piece.movepath;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FiniteMovePathTest {

    @DisplayName("목적지까지 이동할 수 있는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions")
    void canMove(FiniteMovePath finiteMovePath, Point from, Point to, boolean expected) {
        //given & when
        boolean actual = finiteMovePath.canMove(from, to);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("목적지까지 가는 좌표를 반환할 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovePoints")
    void movePoints(Point from, Point to, List<Point> expected) {
        //given
        FiniteMovePath finiteMovePath = new FiniteMovePath(Direction.UP, Direction.RIGHT);

        //when
        List<Point> actual = finiteMovePath.movePoints(from, to);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(new FiniteMovePath(Direction.UP, Direction.RIGHT),
                        Fixtures.NINE_ONE, Fixtures.EIGHT_TWO,
                        true),
                Arguments.of(new FiniteMovePath(Direction.UP, Direction.UP),
                        Fixtures.NINE_ONE, Fixtures.SEVEN_ONE,
                        true),
                Arguments.of(new FiniteMovePath(Direction.UP, Direction.UP),
                        Fixtures.NINE_ONE, Fixtures.SIX_ONE,
                        false)
        );
    }

    private static Stream<Arguments> provideMovePoints() {
        return Stream.of(
                Arguments.of(Fixtures.NINE_ONE, Fixtures.EIGHT_TWO,
                        List.of(Fixtures.EIGHT_ONE, Fixtures.EIGHT_TWO)),
                Arguments.of(Fixtures.FOUR_TWO, Fixtures.THREE_THREE,
                        List.of(Fixtures.THREE_TWO, Fixtures.THREE_THREE))
        );
    }
}