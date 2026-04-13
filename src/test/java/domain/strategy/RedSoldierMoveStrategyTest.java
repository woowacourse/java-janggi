package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RedSoldierMoveStrategyTest {

    private static final Position RED_SOLDIER_POSITION = new Position(3, 2);

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸 이동할 수 있다.")
    void redSoldier_can_move_test(Position soldierPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(soldierPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithinPalaceCornerPositions")
    @DisplayName("한나라 졸이 적팀 궁성 왼쪽 모서리에 있는 경우, 하, 좌우, 궁성 중앙 대각 이동이 가능하다.")
    void redSoldier_within_palace_corner_can_move_test(Position soldierPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(soldierPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithinPalaceCenterPositions")
    @DisplayName("한나라 졸이 적팀 궁성 중앙에 있는 경우, 하, 좌우, 왼-오 아래 대각 이동이 가능하다.")
    void redSoldier_within_palace_center_can_move_test(Position soldierPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(soldierPosition, destination)).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 적팀 궁성 내부에서 앞으로 이동할 수 없다.")
    void redSoldier_within_palace_forward_cannot_move_test() {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();
        Position currentPosition = new Position(8, 4);
        Position cannotMoveUpCrossLeft = new Position(7, 3);
        Position cannotMoveUp = new Position(7, 4);
        Position cannotMoveUpCrossRight = new Position(7, 5);

        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveUpCrossLeft)).isFalse();
        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveUp)).isFalse();
        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveUpCrossRight)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void redSoldier_cannot_move_test(Position soldierPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(soldierPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("졸은 한 칸만 이동할 수 있다. 한 칸만 이동한다면 이동 경로 규칙은 항상 true이다.")
    void redSoldier_can_move_hasValidPathTo_always_true_test(Position soldierPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(soldierPosition, destination, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.down()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.left()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.right()));
    }

    private static Stream<Arguments> moveableWithinPalaceCornerPositions() {
        Position greenPalaceCornerPosition = new Position(7, 3);
        return Stream.of(Arguments.arguments(greenPalaceCornerPosition, greenPalaceCornerPosition.down()),
                Arguments.arguments(greenPalaceCornerPosition, greenPalaceCornerPosition.left()),
                Arguments.arguments(greenPalaceCornerPosition, greenPalaceCornerPosition.right()),
                Arguments.arguments(greenPalaceCornerPosition, greenPalaceCornerPosition.downCrossRight()));
    }

    private static Stream<Arguments> moveableWithinPalaceCenterPositions() {
        Position greenPalaceCenterPosition = new Position(8, 4);
        return Stream.of(Arguments.arguments(greenPalaceCenterPosition, greenPalaceCenterPosition.down()),
                Arguments.arguments(greenPalaceCenterPosition, greenPalaceCenterPosition.left()),
                Arguments.arguments(greenPalaceCenterPosition, greenPalaceCenterPosition.right()),
                Arguments.arguments(greenPalaceCenterPosition, greenPalaceCenterPosition.downCrossRight()),
                Arguments.arguments(greenPalaceCenterPosition, greenPalaceCenterPosition.downCrossLeft()));
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.up()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.up().up()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.left().left()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.up().left()),
                Arguments.arguments(RED_SOLDIER_POSITION, RED_SOLDIER_POSITION.up().right()));
    }

}
