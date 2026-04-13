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

class GreenSoldierMoveStrategyTest {

    private static final Position GREEN_SOLIDER_POSITION = new Position(6, 2);

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸 이동할 수 있다.")
    void greenSoldier_can_move_test(Position guardPosition, Position destination) {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithinPalaceCornerPositions")
    @DisplayName("초나라 졸이 적팀 궁성 왼쪽 모서리에 있는 경우, 상, 좌우, 궁성 중앙 대각 이동이 가능하다.")
    void greenSoldier_within_palace_corner_can_move_test(Position guardPosition, Position destination) {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithinPalaceCenterPositions")
    @DisplayName("초나라 졸이 적팀 궁성 중앙에 있는 경우, 상, 좌우, 왼-오 대각 이동이 가능하다.")
    void greenSoldier_within_palace_center_can_move_test(Position guardPosition, Position destination) {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 적팀 궁성 내부에서 뒤로 이동할 수 없다.")
    void greenSoldier_within_palace_back_cannot_move_test() {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();
        Position currentPosition = new Position(1, 4);
        Position cannotMoveDownCrossLeft = new Position(2, 3);
        Position cannotMoveDown = new Position(2, 4);
        Position cannotMoveDownCrossRight = new Position(2, 5);

        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveDownCrossLeft)).isFalse();
        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveDown)).isFalse();
        assertThat(moveStrategy.canMoveTo(currentPosition, cannotMoveDownCrossRight)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void greenSoldier_cannot_move_test(Position guardPosition, Position wrongTarget) {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("졸은 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void greenSoldier_can_move_hasValidPathTo_always_true_test(Position guardPosition, Position destination) {
        GreenSoldierMoveStrategy moveStrategy = new GreenSoldierMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, destination, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.up()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.left()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.right()));
    }

    private static Stream<Arguments> moveableWithinPalaceCornerPositions() {
        Position redPalaceCornerPosition = new Position(2, 3);
        return Stream.of(Arguments.arguments(redPalaceCornerPosition, redPalaceCornerPosition.up()),
                Arguments.arguments(redPalaceCornerPosition, redPalaceCornerPosition.left()),
                Arguments.arguments(redPalaceCornerPosition, redPalaceCornerPosition.right()),
                Arguments.arguments(redPalaceCornerPosition, redPalaceCornerPosition.upCrossRight()));
    }

    private static Stream<Arguments> moveableWithinPalaceCenterPositions() {
        Position redPalaceCenterPosition = new Position(1, 4);
        return Stream.of(Arguments.arguments(redPalaceCenterPosition, redPalaceCenterPosition.up()),
                Arguments.arguments(redPalaceCenterPosition, redPalaceCenterPosition.left()),
                Arguments.arguments(redPalaceCenterPosition, redPalaceCenterPosition.right()),
                Arguments.arguments(redPalaceCenterPosition, redPalaceCenterPosition.upCrossRight()),
                Arguments.arguments(redPalaceCenterPosition, redPalaceCenterPosition.upCrossLeft()));
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.down()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.up().up()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.left().left()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.up().left()),
                Arguments.arguments(GREEN_SOLIDER_POSITION, GREEN_SOLIDER_POSITION.up().right()));
    }

}
