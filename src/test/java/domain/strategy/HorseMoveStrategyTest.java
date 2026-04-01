package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("마는 현재 위치 기준 상하좌우 한 칸 이동 후, 진행 방향 대각선으로 이동할 수 있어야 한다.")
    void horse_move_test(Position position, Position expectedTarget) {
        HorseMoveStrategy moveStrategy = HorseMoveStrategy.of(position);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("마는 현재 위치 기준 상하좌우 한 칸 이동 후, 진행 방향 대각선 외에는 이동할 수 없어야 한다.")
    void horse_move_test_negative(Position position, Position expectedTarget) {
        HorseMoveStrategy moveStrategy = HorseMoveStrategy.of(position);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("moveablePositionsAndBlockedPositions")
    @DisplayName("마의 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(막힘)")
    void horse_blocked_route_test(Position position, Position expectedTarget, List<Position> piecePositions) {
        HorseMoveStrategy moveStrategy = HorseMoveStrategy.of(position);

        assertThat(moveStrategy.isPathRestricted(expectedTarget, piecePositions)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveablePositionsAndNonBlockedPositions")
    @DisplayName("마의 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(안 막힘)")
    void horse_non_blocked_route_test(Position position, Position expectedTarget, List<Position> piecePositions) {
        HorseMoveStrategy moveStrategy = HorseMoveStrategy.of(position);

        assertThat(moveStrategy.isPathRestricted(expectedTarget, piecePositions)).isFalse();
    }


    private static Stream<Arguments> moveablePositions() {
        Position midPosition = Position.of(4, 4);
        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft()),
                Arguments.arguments(midPosition, midPosition.up().upCrossRight()),
                Arguments.arguments(midPosition, midPosition.down().downCrossLeft()),
                Arguments.arguments(midPosition, midPosition.down().downCrossRight()),

                Arguments.arguments(midPosition, midPosition.left().upCrossLeft()),
                Arguments.arguments(midPosition, midPosition.left().downCrossLeft()),
                Arguments.arguments(midPosition, midPosition.right().upCrossRight()),
                Arguments.arguments(midPosition, midPosition.right().downCrossRight())
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        Position midPosition = Position.of(4, 4);
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

    private static Stream<Arguments> moveablePositionsAndNonBlockedPositions() {
        return moveablePositions().map(arguments -> {
            Object[] args = arguments.get();
            return Arguments.arguments(args[0], args[1], List.of());
        });
    }

    private static Stream<Arguments> moveablePositionsAndBlockedPositions() {
        Position midPosition = Position.of(4, 4);

        return Stream.of(
                Arguments.arguments(midPosition, midPosition.up().upCrossLeft(), List.of(midPosition.up())),
                Arguments.arguments(midPosition, midPosition.up().upCrossRight(), List.of(midPosition.up())),
                Arguments.arguments(midPosition, midPosition.down().downCrossLeft(), List.of(midPosition.down())),
                Arguments.arguments(midPosition, midPosition.down().downCrossRight(), List.of(midPosition.down())),

                Arguments.arguments(midPosition, midPosition.left().upCrossLeft(), List.of(midPosition.left())),
                Arguments.arguments(midPosition, midPosition.left().downCrossLeft(), List.of(midPosition.left())),
                Arguments.arguments(midPosition, midPosition.right().upCrossRight(), List.of(midPosition.right())),
                Arguments.arguments(midPosition, midPosition.right().downCrossRight(), List.of(midPosition.right()))
        );
    }
}
