package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("상 기물은 현재 위치 기준 상하좌우 한 칸 이동 후, 진행 방향 같은 대각선으로 2번 이동할 수 있어야 한다.")
    void elephant_can_move_test(Position current, Position destination) {
        ElephantMoveStrategy moveStrategy = ElephantMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("상 기물은 현재 위치 기준 상하좌우 한 칸 이동 후, 진행 방향 같은 대각선 2번 외에는 이동할 수 없어야 한다.")
    void elephant_cannot_move_test_negative(Position current, Position destination) {
        ElephantMoveStrategy moveStrategy = ElephantMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("blockedElephantPaths")
    @DisplayName("상 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(막힘)")
    void elephant_cannot_move_blocked_route_test(Position current, Position destination, List<Position> obstacles) {
        ElephantMoveStrategy moveStrategy = ElephantMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("clearElephantPath")
    @DisplayName("상 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(안 막힘)")
    void elephant_can_move_hasValidPathTo_non_blocked_route_test(Position current, Position destination, List<Position> obstacles) {
        ElephantMoveStrategy moveStrategy = ElephantMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isTrue();
    }


    private static Stream<Arguments> moveablePositions() {
        Position midPosition = new Position(4, 4);
        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft().upCrossLeft()),
                Arguments.arguments(midPosition, midPosition.up().upCrossRight().upCrossRight()),
                Arguments.arguments(midPosition, midPosition.down().downCrossLeft().downCrossLeft()),
                Arguments.arguments(midPosition, midPosition.down().downCrossRight().downCrossRight()),

                Arguments.arguments(midPosition, midPosition.left().upCrossLeft().upCrossLeft()),
                Arguments.arguments(midPosition, midPosition.left().downCrossLeft().downCrossLeft()),
                Arguments.arguments(midPosition, midPosition.right().upCrossRight().upCrossRight()),
                Arguments.arguments(midPosition, midPosition.right().downCrossRight().downCrossRight())
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        Position midPosition = new Position(4, 4);
        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft().up()),
                Arguments.arguments(midPosition, midPosition.up().upCrossRight().up()),
                Arguments.arguments(midPosition, midPosition.down().downCrossLeft().down()),
                Arguments.arguments(midPosition, midPosition.down().downCrossRight().down()),

                Arguments.arguments(midPosition, midPosition.left().upCrossLeft().left()),
                Arguments.arguments(midPosition, midPosition.left().downCrossLeft().left()),
                Arguments.arguments(midPosition, midPosition.right().upCrossRight().left()),
                Arguments.arguments(midPosition, midPosition.right().downCrossRight().left())
        );
    }

    private static Stream<Arguments> blockedElephantPaths() {
        Position midPosition = new Position(4, 4);

        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft().upCrossLeft(),
                        List.of(midPosition.up())),
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft().upCrossLeft(),
                        List.of(midPosition.up().upCrossLeft()))
        );
    }

    private static Stream<Arguments> clearElephantPath() {
        Position midPosition = new Position(4, 4);

        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft().upCrossLeft(), List.of())
        );
    }
}
