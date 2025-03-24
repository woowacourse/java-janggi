package janggi.domain.piece.movepath;

import janggi.domain.Fixtures;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EndlessMovePathTest {

    @DisplayName("목적지까지 갈 수 있는지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions")
    void canMove(Point from, Point to, boolean expected) {
        //given
        EndlessMovePath endlessMovePath = new EndlessMovePath(Direction.UP);

        //when
        boolean result = endlessMovePath.canMove(from, to);

        //then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @DisplayName("목적지까지 가는 좌표를 반환할 수 있다")
    @ParameterizedTest
    @MethodSource("provideMovePoints")
    void movePoints(Point from, Point to, List<Point> expected) {
        //given
        EndlessMovePath endlessMovePath = new EndlessMovePath(Direction.UP);

        //when
        List<Point> actual = endlessMovePath.movePoints(from, to);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(Fixtures.NINE_ONE, Fixtures.ONE_ONE, true),
                Arguments.of(Fixtures.NINE_ONE, Fixtures.ONE_TWO, false)
        );
    }

    private static Stream<Arguments> provideMovePoints() {
        return Stream.of(
                Arguments.of(Fixtures.NINE_ONE, Fixtures.SIX_ONE,
                        List.of(Fixtures.EIGHT_ONE, Fixtures.SEVEN_ONE, Fixtures.SIX_ONE)),
                Arguments.of(Fixtures.NINE_ONE, Fixtures.EIGHT_ONE,
                        List.of(Fixtures.EIGHT_ONE))
        );
    }
}