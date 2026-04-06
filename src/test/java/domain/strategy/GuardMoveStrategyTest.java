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
        GuardMoveStrategy moveStrategy = new GuardMoveStrategy();
        Position guardPosition = new Position(8, 3);
        Position palaceOutsideGuardDestination = new Position(8, 2);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveStrategy.canMoveTo(guardPosition, palaceOutsideGuardDestination))
                .withMessage("[ERROR] 사는 궁성 내부에서만 움직일 수 있다.");
    }

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("사는 현재 위치 기준 상하좌우 한 칸 이동할 수 있다.")
    void guard_can_move_test(Position guardPosition, Position destination) {
        GuardMoveStrategy moveStrategy = new GuardMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("사는 현재 위치 기준 상하좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void guard_cannot_move_test(Position guardPosition, Position wrongTarget) {
        GuardMoveStrategy moveStrategy = new GuardMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("사는 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void guard_can_move_hasValidPathTo_always_true_test(Position guardPosition, Position wrongTarget) {
        GuardMoveStrategy moveStrategy = new GuardMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, wrongTarget, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        Position palaceRedCenter = new Position(1, 4);

        return Stream.of(
                Arguments.arguments(palaceRedCenter, palaceRedCenter.up()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.down()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.left()),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.right())
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
