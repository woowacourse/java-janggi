package janggi.direction;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.value.Position;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BeelineDirectionTest {

    static final Position START_POSITION = new Position(4, 4);

    @ParameterizedTest
    @DisplayName("시작점과 목적지를 통해 방향을 구할 수 있다.")
    @MethodSource
    void canCalculateDirection(BeelineDirection expectedDirection, Position destination) {
        BeelineDirection actualDirection = BeelineDirection.parse(START_POSITION, destination);
        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    static Stream<Arguments> canCalculateDirection() {
        return Stream.of(
                Arguments.of(BeelineDirection.RIGHT, new Position(START_POSITION.x() + 1, START_POSITION.y())),
                Arguments.of(BeelineDirection.LEFT, new Position(START_POSITION.x() - 1, START_POSITION.y())),
                Arguments.of(BeelineDirection.DOWN, new Position(START_POSITION.x(), START_POSITION.y() + 1)),
                Arguments.of(BeelineDirection.UP, new Position(START_POSITION.x(), START_POSITION.y() - 1)),
                Arguments.of(BeelineDirection.NONE, new Position(START_POSITION.x() - 1, START_POSITION.y() - 1)),
                Arguments.of(BeelineDirection.RIGHT, new Position(8, START_POSITION.y())),
                Arguments.of(BeelineDirection.LEFT, new Position(0, START_POSITION.y())),
                Arguments.of(BeelineDirection.DOWN, new Position(START_POSITION.x(), 9)),
                Arguments.of(BeelineDirection.UP, new Position(START_POSITION.x(), 0)),
                Arguments.of(BeelineDirection.NONE, new Position(0, 0))

        );
    }


    @ParameterizedTest
    @DisplayName("방향을 통해 시작점과 목적지 사이의 위치값들을 구할 수 있다.")
    @MethodSource
    void canCalculatePositionsByDirection(
            BeelineDirection direction,
            Position destination,
            List<Position> positionsInPath
    ) {
        BiFunction<Position, Position, List<Position>> pathCalculationMethod = direction.getCalculatePositionsInPath();
        List<Position> actualPositions = pathCalculationMethod.apply(START_POSITION, destination);
        assertThat(actualPositions).containsExactlyInAnyOrderElementsOf(positionsInPath);
    }

    static Stream<Arguments> canCalculatePositionsByDirection() {
        return Stream.of(
                Arguments.of(
                        BeelineDirection.RIGHT,
                        new Position(START_POSITION.x() + 3, START_POSITION.y()),
                        List.of(new Position(START_POSITION.x() + 1, START_POSITION.y()),
                                new Position(START_POSITION.x() + 2, START_POSITION.y()))),
                Arguments.of(
                        BeelineDirection.LEFT,
                        new Position(START_POSITION.x() - 3, START_POSITION.y()),
                        List.of(new Position(START_POSITION.x() - 1, START_POSITION.y()),
                                new Position(START_POSITION.x() - 2, START_POSITION.y()))),
                Arguments.of(
                        BeelineDirection.DOWN,
                        new Position(START_POSITION.x(), START_POSITION.y() + 3),
                        List.of(new Position(START_POSITION.x(), START_POSITION.y() + 1),
                                new Position(START_POSITION.x(), START_POSITION.y() + 2))),
                Arguments.of(
                        BeelineDirection.UP,
                        new Position(START_POSITION.x(), START_POSITION.y() - 3),
                        List.of(new Position(START_POSITION.x(), START_POSITION.y() - 1),
                                new Position(START_POSITION.x(), START_POSITION.y() - 2))),
                Arguments.of(
                        BeelineDirection.NONE,
                        new Position(START_POSITION.x() - 1, START_POSITION.y() - 1),
                        List.of())
        );
    }
}