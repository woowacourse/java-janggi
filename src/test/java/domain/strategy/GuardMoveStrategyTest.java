package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사는 궁성 외부로는 움직일 수 없다.")
    void cannot_move_guard_destination_outside_palace_test() {
        MoveStrategy moveStrategy = new PalaceMoveStrategy();
        Position guardPosition = new Position(8, 3);
        Position palaceOutsideGuardDestination = new Position(8, 2);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveStrategy.canMoveTo(guardPosition, palaceOutsideGuardDestination))
                .withMessage("[ERROR] 해당 기물은 궁성 내부에서만 움직일 수 있다.");
    }

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("사가 궁성 중간 위치인 경우 상하좌우 및 대각 한 칸 이동이 가능하다.")
    void guard_center_can_move_test(Position guardPosition, Position destination) {
        MoveStrategy moveStrategy = new PalaceMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableCornerPositions")
    @DisplayName("사가 궁성 모서리 위치인 경우, 궁성 내부에서만 이동 가능하며 궁성 중앙으로 이동이 가능하다.")
    void guard_corner_can_move_test(Position guardPosition, Position destination) {
        MoveStrategy moveStrategy = new PalaceMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("사는 한 칸, 궁성 대각 이동 외에 이동할 수 없다.")
    void guard_cannot_move_test(Position guardPosition, Position wrongTarget) {
        MoveStrategy moveStrategy = new PalaceMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("사는 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void guard_can_move_hasValidPathTo_always_true_test(Position guardPosition, Position wrongTarget) {
        MoveStrategy moveStrategy = new PalaceMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, wrongTarget, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        Position palaceRedCenter = new Position(1, 4);

        return Stream.of(
                Arguments.arguments(palaceRedCenter, palaceRedCenter.up()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.down()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.left()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.right()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.upCrossLeft()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.upCrossRight()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.downCrossRight()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.downCrossLeft())
        );
    }

    private static Stream<Arguments> moveableCornerPositions() {
        Position palaceRedCorner = new Position(2, 3);

        return Stream.of(
                Arguments.arguments(palaceRedCorner, palaceRedCorner.up()),
                Arguments.arguments(palaceRedCorner, palaceRedCorner.right()),
                Arguments.arguments(palaceRedCorner, palaceRedCorner.upCrossRight())
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        Position insidePalacePosition = new Position(0, 4);
        return Stream.of(
                Arguments.arguments(insidePalacePosition, insidePalacePosition.down().down()),
                Arguments.arguments(insidePalacePosition, insidePalacePosition.down().left()),
                Arguments.arguments(insidePalacePosition, insidePalacePosition.down().right()),
                Arguments.arguments(insidePalacePosition, insidePalacePosition.down().downCrossRight()),
                Arguments.arguments(insidePalacePosition, insidePalacePosition.down().downCrossLeft())
        );
    }
}
