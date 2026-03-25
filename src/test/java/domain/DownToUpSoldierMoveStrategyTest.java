package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.strategy.DownToUpSoldierMoveStrategy;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DownToUpSoldierMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸 이동할 수 있다.")
    void soldier_move_test(Position guardPosition, Position expectedTarget) {
        DownToUpSoldierMoveStrategy moveStrategy = DownToUpSoldierMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void soldier_move_test_negative(Position guardPosition, Position wrongTarget) {
        DownToUpSoldierMoveStrategy moveStrategy = DownToUpSoldierMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
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
