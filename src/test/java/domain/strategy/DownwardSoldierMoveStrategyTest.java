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

class DownwardSoldierMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸 이동할 수 있다.")
    void soldier_move_test(Position guardPosition, Position expectedTarget) {
        DownwardSoldierMoveStrategy moveStrategy = DownwardSoldierMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.canMoveTo(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void soldier_move_test_negative(Position guardPosition, Position wrongTarget) {
        DownwardSoldierMoveStrategy moveStrategy = DownwardSoldierMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.canMoveTo(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("졸은 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void hasValidPathTo_always_true_test(Position guardPosition, Position wrongTarget) {
        DownwardSoldierMoveStrategy moveStrategy = DownwardSoldierMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.hasValidPathTo(wrongTarget, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(6, 2), Position.of(6, 1)),
                Arguments.arguments(Position.of(6, 2), Position.of(6, 3)),
                Arguments.arguments(Position.of(6, 2), Position.of(5, 2))
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(6, 2), Position.of(6, 4)),
                Arguments.arguments(Position.of(6, 2), Position.of(1, 2)),
                Arguments.arguments(Position.of(6, 2), Position.of(5, 3)),

                Arguments.arguments(Position.of(6, 2), Position.of(7, 2)),
                Arguments.arguments(Position.of(6, 2), Position.of(8, 3))
        );
    }

}
