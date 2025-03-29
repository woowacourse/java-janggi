package janggi.value;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DirectionTest {

    static final Position CENTER = new Position(0, 0);
    static final Position LEFT = new Position(-2, 0);
    static final Position RIGHT = new Position(2, 0);
    static final Position UP = new Position(0, -2);
    static final Position DOWN = new Position(0, 2);
    static final Position UP_LEFT = new Position(-2, -2);
    static final Position UP_RIGHT = new Position(2, -2);
    static final Position DOWN_LEFT = new Position(-2, 2);
    static final Position DOWN_RIGHT = new Position(2, 2);

    @DisplayName("방향을 가지고 시작점과 끝점 사이의 위치값들을 구할 수 있다.")
    @ParameterizedTest
    @MethodSource
    void calculatePositionInDirection(
            Direction direction,
            Position end,
            List<Position> expectedResult
    ) {
        List<Position> actualResult = direction.calculatePositionInDirection(CENTER, end);
        assertThat(actualResult).containsExactlyElementsOf(expectedResult);
    }

    static Stream<Arguments> calculatePositionInDirection() {
        return Stream.of(
                Arguments.of(
                        Direction.LEFT, LEFT, List.of(CENTER, new Position(-1, 0), LEFT)),
                Arguments.of(
                        Direction.RIGHT, RIGHT, List.of(CENTER, new Position(1, 0), RIGHT)),
                Arguments.of(
                        Direction.UP, UP, List.of(CENTER, new Position(0, -1), UP)),
                Arguments.of(
                        Direction.DOWN, DOWN, List.of(CENTER, new Position(0, 1), DOWN)),
                Arguments.of(
                        Direction.UP_LEFT, UP_LEFT, List.of(CENTER, new Position(-1, -1), UP_LEFT)),
                Arguments.of(
                        Direction.DOWN_RIGHT, DOWN_RIGHT, List.of(CENTER, new Position(1, 1), DOWN_RIGHT)),
                Arguments.of(
                        Direction.UP_RIGHT, UP_RIGHT, List.of(CENTER, new Position(1, -1), UP_RIGHT)),
                Arguments.of(
                        Direction.DOWN_LEFT, DOWN_LEFT, List.of(CENTER, new Position(-1, 1), DOWN_LEFT))
        );
    }
}