package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("장군은 현재 위치 기준 상하좌우 한 칸 이동할 수 있다.")
    void general_move_test(Position guardPosition, Position destination) {
        GeneralMoveStrategy moveStrategy = new GeneralMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("장군은 현재 위치 기준 상하좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void general_move_test_negative(Position guardPosition, Position wrongTarget) {
        GeneralMoveStrategy moveStrategy = new GeneralMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("장군은 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void hasValidPathTo_always_true_test(Position guardPosition, Position destination) {
        GeneralMoveStrategy moveStrategy = new GeneralMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, destination, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(
                Arguments.arguments(new Position(1, 4), new Position(1, 5)),
                Arguments.arguments(new Position(1, 4), new Position(1, 3)),
                Arguments.arguments(new Position(1, 4), new Position(2, 4)),
                Arguments.arguments(new Position(1, 4), new Position(0, 4))
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(
                Arguments.arguments(new Position(1, 4), new Position(1, 2)),
                Arguments.arguments(new Position(1, 4), new Position(3, 3)),
                Arguments.arguments(new Position(1, 4), new Position(5, 5)),
                Arguments.arguments(new Position(1, 4), new Position(7, 7))
        );
    }
}
