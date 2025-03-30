package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.move.Path;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PathTest {

    static Stream<Arguments> canReachFromToTest() {
        return Stream.of(
                Arguments.of(
                        List.of(Direction.UP, Direction.RIGHT_UP, Direction.DOWN),
                        Position.of(1, 1),
                        Position.of(2, 2),
                        true
                ),
                Arguments.of(
                        List.of(Direction.UP, Direction.RIGHT_UP, Direction.DOWN),
                        Position.of(1, 1),
                        Position.of(2, 3),
                        false
                ),
                Arguments.of(
                        List.of(Direction.UP, Direction.UP, Direction.LEFT),
                        Position.of(1, 1),
                        Position.of(3, 0),
                        true
                ),
                Arguments.of(
                        List.of(Direction.UP, Direction.UP, Direction.LEFT),
                        Position.of(1, 1),
                        Position.of(3, 3),
                        false
                ),
                Arguments.of(
                        List.of(Direction.UP, Direction.UP, Direction.LEFT),
                        Position.of(8, 1),
                        Position.of(8, 0),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 경로를 통해 도착 가능 여부를 반환한다")
    void canReachFromToTest(List<Direction> directions, Position startPosition, Position endPosition,
                            boolean expected) {
        // given
        Path path = new Path(directions);

        // when
        boolean actual = path.canReachFromTo(startPosition, endPosition);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> findPathPositionsFromTest() {
        return Stream.of(
                Arguments.of(
                        List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT),
                        Position.of(0, 0),
                        List.of(Position.of(1, 0), Position.of(2, 1))
                ),
                Arguments.of(
                        List.of(Direction.LEFT, Direction.RIGHT_UP, Direction.DOWN),
                        Position.of(3, 3),
                        List.of(Position.of(3, 2), Position.of(4, 3))
                ),
                Arguments.of(
                        List.of(Direction.RIGHT, Direction.RIGHT_UP, Direction.UP),
                        Position.of(4, 0),
                        List.of(Position.of(4, 1), Position.of(5, 2))
                )
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 경로의 중간 경로를 반환한다")
    void findPathPositionsFromTest(List<Direction> directions, Position startPosition,
                                   List<Position> expected) {
        // given
        Path path = new Path(directions);

        // when
        List<Position> intermediatePositions = path.findPathPositionsFrom(startPosition);

        // then
        assertThat(intermediatePositions).containsExactlyInAnyOrderElementsOf(expected);
    }
}
