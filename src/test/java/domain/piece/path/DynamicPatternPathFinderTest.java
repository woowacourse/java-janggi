package domain.piece.path;

import static fixtures.PositionFixture.D0;
import static fixtures.PositionFixture.D1;
import static fixtures.PositionFixture.D3;
import static fixtures.PositionFixture.D4;
import static fixtures.PositionFixture.D5;
import static fixtures.PositionFixture.D6;
import static fixtures.PositionFixture.E1;
import static fixtures.PositionFixture.E2;
import static fixtures.PositionFixture.F2;
import static fixtures.PositionFixture.H0;
import static fixtures.PositionFixture.H4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynamicPatternPathFinderTest {

    private DynamicPatternPathFinder pathFinder;

    @BeforeEach
    void beforeEach() {
        List<Direction> directions = List.of(Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
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
        pathFinder = new DynamicPatternPathFinder(directions, palaceDirections);
    }

    @Test
    @DisplayName("지나온 경로 반환 테스트")
    void findIntermediatePositionsTest() {
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(D3, D6);

        assertThat(intermediatePositions).containsExactly(D4, D5);
    }

    @Test
    @DisplayName("갈 수 없는 곳에 이동을 검증하면 예외가 발생한다.")
    void findIntermediatePositionsException() {
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(D3, H0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");

    }

    @Test
    @DisplayName("시작 위치가 궁 안에 있으면 특수하게 움직일 수 있다.")
    void findIntermediatePositionsWhenPalaceTest() {
        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(D0, F2);

        assertThat(intermediatePositions).containsExactly(E1);
    }

    @Test
    @DisplayName("시작 위치가 궁 안에 있어도 특수 위치에 없으면 특수하게 움직일 수 없다.")
    void findIntermediatePositionsWhenPalaceException() {
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(D1, E2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("시작 위치가 궁 특수 위치 일때 궁 밖으로 특수 방향으로 나가려하면 예외가 발생한다.")
    void findIntermediatePositionsWhenPalaceException2() {
        assertThatThrownBy(() -> pathFinder.findIntermediatePositions(D0, H4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }
}