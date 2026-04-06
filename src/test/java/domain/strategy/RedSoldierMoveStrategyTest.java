package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RedSoldierMoveStrategyTest {

    private static final Position redSoliderPosition = new Position(3, 2);

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸 이동할 수 있다.")
    void redSoldier_can_move_test(Position guardPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void redSoldier_cannot_move_test(Position guardPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("졸은 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void redSoldier_can_move_hasValidPathTo_always_true_test(Position guardPosition, Position destination) {
        RedSoldierMoveStrategy moveStrategy = new RedSoldierMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, destination, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(Arguments.arguments(redSoliderPosition, redSoliderPosition.down()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.left()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.right()));
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(Arguments.arguments(redSoliderPosition, redSoliderPosition.up()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.down().down()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.left().left()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.down().left()),
                Arguments.arguments(redSoliderPosition, redSoliderPosition.down().right()));
    }

}
