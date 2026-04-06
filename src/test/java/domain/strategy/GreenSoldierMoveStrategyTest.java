package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GreenSoldierMoveStrategyTest {

    private static final Position greenSoliderPosition = new Position(6, 2);

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸 이동할 수 있다.")
    void greenSoldier_can_move_test(Position guardPosition, Position destination) {
        GreendSoldierMoveStrategy moveStrategy = new GreendSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void greenSoldier_cannot_move_test(Position guardPosition, Position wrongTarget) {
        GreendSoldierMoveStrategy moveStrategy = new GreendSoldierMoveStrategy();

        assertThat(moveStrategy.canMoveTo(guardPosition, wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("졸은 한 칸만 이동하므로 이동 경로 규칙이 항상 true이다.")
    void greenSoldier_can_move_hasValidPathTo_always_true_test(Position guardPosition, Position destination) {
        GreendSoldierMoveStrategy moveStrategy = new GreendSoldierMoveStrategy();

        assertThat(moveStrategy.hasValidPathTo(guardPosition, destination, List.of())).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(Arguments.arguments(greenSoliderPosition, greenSoliderPosition.up()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.left()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.right()));
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(Arguments.arguments(greenSoliderPosition, greenSoliderPosition.down()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.up().up()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.left().left()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.up().left()),
                Arguments.arguments(greenSoliderPosition, greenSoliderPosition.up().right()));
    }

}
