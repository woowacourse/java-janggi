package domain.piece.path;

import static fixtures.PositionFixture.D0;
import static fixtures.PositionFixture.D1;
import static fixtures.PositionFixture.D2;
import static fixtures.PositionFixture.D4;
import static fixtures.PositionFixture.D7;
import static fixtures.PositionFixture.D9;
import static fixtures.PositionFixture.E0;
import static fixtures.PositionFixture.E1;
import static fixtures.PositionFixture.E2;
import static fixtures.PositionFixture.E3;
import static fixtures.PositionFixture.E4;
import static fixtures.PositionFixture.E5;
import static fixtures.PositionFixture.E8;
import static fixtures.PositionFixture.F0;
import static fixtures.PositionFixture.F1;
import static fixtures.PositionFixture.F2;
import static fixtures.PositionFixture.F4;
import static fixtures.PositionFixture.F7;
import static fixtures.PositionFixture.F9;
import static fixtures.PositionFixture.G1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FixedSingleMovePathFinderTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("가능한 경로라면 예외를 발생시키지 않는다.")
    void findIntermediatePositionsTest(Position from, Position to) {
        PathFinder pathFinder = createAnyWayFixedSingleMovePathFinder();
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        assertThat(intermediatePositions).isEqualTo(List.of());
    }

    private static Stream<Arguments> findIntermediatePositionsTest() {
        return Stream.of(
                Arguments.of(E4, E3),
                Arguments.of(E4, E5),
                Arguments.of(E4, F4),
                Arguments.of(E4, D4),
                Arguments.of(D0, E1),
                Arguments.of(F0, E1),
                Arguments.of(D2, E1),
                Arguments.of(F2, E1),
                Arguments.of(E1, D0),
                Arguments.of(E1, F0),
                Arguments.of(E1, D2),
                Arguments.of(E1, F2)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("불가능한 경로라면 예외가 발생한다.")
    void findIntermediatePositionsException(Position from, Position to) {
        PathFinder pathFinder = createAnyWayFixedSingleMovePathFinder();
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> findIntermediatePositionsException() {
        return Stream.of(
                Arguments.of(D1, E2),
                Arguments.of(G1, E2),
                Arguments.of(D1, E0),
                Arguments.of(G1, E0)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁 특수 위치에서 특수 이동이 가능하다.")
    void findIntermediatePositionsWhenPalace(Position from, Position to) {
        PathFinder pathFinder = createAnyWayFixedSingleMovePathFinder();
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        assertThat(intermediatePositions).isEqualTo(List.of());
    }

    private static Stream<Arguments> findIntermediatePositionsWhenPalace() {
        return Stream.of(
                Arguments.of(D0, E1),
                Arguments.of(F1, E1),
                Arguments.of(D1, E1),
                Arguments.of(F1, E1),
                Arguments.of(E1, D0),
                Arguments.of(E1, F1),
                Arguments.of(E1, D1),
                Arguments.of(E1, F1)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁 특수 위치가 아니면 특수 이동이 불가능하다.")
    void findIntermediatePositionsWhenPalaceException(Position from, Position to) {
        PathFinder pathFinder = createAnyWayFixedSingleMovePathFinder();
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> findIntermediatePositionsWhenPalaceException() {
        return Stream.of(
                Arguments.of(D1, E0),
                Arguments.of(D1, E2),
                Arguments.of(F1, E0),
                Arguments.of(F1, E2),
                Arguments.of(E0, D1),
                Arguments.of(E0, F1),
                Arguments.of(E2, D1),
                Arguments.of(E2, F1)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("쫄일때 궁 특수 위치에서 특수 이동이 가능하다.")
    void findIntermediatePositionsWhenPalace2(Position from, Position to) {
        PathFinder pathFinder = createSoliderSingleMovePathFinder();
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(from, to);
        assertThat(intermediatePositions).isEqualTo(List.of());
    }

    private static Stream<Arguments> findIntermediatePositionsWhenPalace2() {
        return Stream.of(
                Arguments.of(D2, E1),
                Arguments.of(F2, E1),
                Arguments.of(E1, D0),
                Arguments.of(E1, F0),
                Arguments.of(D7, E8),
                Arguments.of(F7, E8),
                Arguments.of(E8, D9),
                Arguments.of(E8, F9)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("쫄일 때 궁 특수 위치가 아니면 특수 이동이 불가능하다.")
    void findIntermediatePositionsWhenPalaceException2(Position from, Position to) {
        PathFinder pathFinder = createSoliderSingleMovePathFinder();
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> findIntermediatePositionsWhenPalaceException2() {
        return Stream.of(
                Arguments.of(D1, E0),
                Arguments.of(D1, E2),
                Arguments.of(F1, E0),
                Arguments.of(F1, E2),
                Arguments.of(E0, D1),
                Arguments.of(E0, F1),
                Arguments.of(E2, D1),
                Arguments.of(E2, F1),
                Arguments.of(E1, D2),
                Arguments.of(E1, F2),
                Arguments.of(D0, E1),
                Arguments.of(F0, E1),
                Arguments.of(D9, E8),
                Arguments.of(F9, E8),
                Arguments.of(E8, D7),
                Arguments.of(E8, F7)
        );
    }

    private PathFinder createAnyWayFixedSingleMovePathFinder() {
        List<Direction> directions = List.of(Direction.RIGHT, Direction.LEFT, Direction.DOWN, Direction.UP);
        Map<Position, List<Direction>> palaceDirections = Map.of(
                Position.of(0, 3), List.of(Direction.RIGHT_UP),
                Position.of(0, 5), List.of(Direction.LEFT_UP),
                Position.of(2, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(2, 5), List.of(Direction.LEFT_DOWN),
                Position.of(1, 4),
                List.of(Direction.RIGHT_UP, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.LEFT_DOWN),
                Position.of(7, 3), List.of(Direction.RIGHT_UP),
                Position.of(7, 5), List.of(Direction.LEFT_UP),
                Position.of(9, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(9, 5), List.of(Direction.LEFT_DOWN),
                Position.of(8, 4),
                List.of(Direction.RIGHT_UP, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.LEFT_DOWN)
        );
        return new FixedSingleMovePathFinder(directions, palaceDirections);
    }

    private PathFinder createSoliderSingleMovePathFinder() {
        List<Direction> directions = List.of(Direction.RIGHT, Direction.LEFT, Direction.DOWN, Direction.UP);
        Map<Position, List<Direction>> palaceDirections = Map.of(
                Position.of(2, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(2, 5), List.of(Direction.LEFT_DOWN),
                Position.of(1, 4), List.of(Direction.RIGHT_DOWN, Direction.LEFT_DOWN),
                Position.of(7, 3), List.of(Direction.RIGHT_UP),
                Position.of(7, 5), List.of(Direction.LEFT_UP),
                Position.of(8, 4), List.of(Direction.RIGHT_UP, Direction.LEFT_UP)
        );
        return new FixedSingleMovePathFinder(directions, palaceDirections);
    }
}