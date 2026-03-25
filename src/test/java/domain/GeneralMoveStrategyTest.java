package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GeneralMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("장군은 현재 위치 기준 상하좌우 한 칸 이동할 수 있다.")
    void general_move_test(Position guardPosition, Position expectedTarget) {
        GeneralMoveStrategy moveStrategy = GeneralMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("장군은 현재 위치 기준 상하좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void general_move_test_negative(Position guardPosition, Position wrongTarget) {
        GeneralMoveStrategy moveStrategy = GeneralMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(1, 4), Position.of(1,5)),
                Arguments.arguments(Position.of(1, 4), Position.of(1,3)),
                Arguments.arguments(Position.of(1, 4), Position.of(2,4)),
                Arguments.arguments(Position.of(1, 4), Position.of(0,4))
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(1, 4), Position.of(1,2)),
                Arguments.arguments(Position.of(1, 4), Position.of(3,3)),
                Arguments.arguments(Position.of(1, 4), Position.of(5,5)),
                Arguments.arguments(Position.of(1, 4), Position.of(7,7))
        );
    }
}
