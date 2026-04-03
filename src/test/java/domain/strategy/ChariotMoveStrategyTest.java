package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import net.bytebuddy.asm.MemberSubstitution.Argument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 내에 위치로 이동할 수 있어야 한다.")
    void chariot_move_test() {
        Position current = Position.of(4, 4);
        Position target = Position.of(9, 4);
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(target)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 이동 이외에 이동할 수 없어야 한다.")
    void chariot_move_test_negative() {
        Position current = Position.of(4, 4);
        Position target = Position.of(9, 5);
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(target)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(막힘)")
    void chariot_blocked_route_test() {
        Position current = Position.of(4, 4);
        Position target = Position.of(4, 9);
        List<Position> piecePositions = List.of(Position.of(4, 8));
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isPathRestricted(target, piecePositions)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(안 막힘)")
    void chariot_non_blocked_route_test() {
        Position current = Position.of(4, 4);
        Position target = Position.of(4, 9);
        List<Position> piecePositions = List.of(Position.of(4, 3));
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isPathRestricted(target, piecePositions)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("moveablePositionsInPalaceCenter")
    @DisplayName("차 기물은 궁성 중앙에서 여덟 방향으로 움직일 수 있어야 한다.(안 막힘)")
    void chariot_non_blocked_route_palace_test(Position current, Position expected) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(expected)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("movableDestinationsOfRedPalaceByNorthWestConner")
    @DisplayName("차 기물은 궁성 북서귀에서 동, 동남, 남 방향으로 움직일 수 있어야 한다.(안 막힘)")
    void chariot_non_blocked_route_palace_north_west_test(Position current, Position expected) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(expected)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDestinationsOfRedPalaceByNorthWestConner")
    @DisplayName("차 기물은 궁성 북서귀에서 동, 동남, 남 방향을 벗어나거나 궁성을 넘어서 대각선으로 움직일 수 없다.(안 막힘)")
    void chariot_non_blocked_route_palace_north_west_test_negative(Position current, Position expected) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isMoveAble(expected)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableConnerDestinationsOfRedPalaceByBlocked")
    @DisplayName("차 기물은 이동 경로에 기물 위치가 포함되는 여부를 반환할 수 있어야 한다.(막힘)")
    void chariot_blocked_route_palace_test(Position current, Position target) {
        List<Position> piecePositions = List.of(Position.of(1, 4));
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.of(current);

        assertThat(moveStrategy.isPathRestricted(target, piecePositions)).isTrue();
    }

    private static Stream<Arguments> moveablePositionsInPalaceCenter() {
        Position redPalaceCenter = Position.of(1, 4);
        Position greenPlaceCenter = Position.of(8, 4);

        return Stream.of(
                Arguments.arguments(redPalaceCenter, redPalaceCenter.up()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.down()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.left()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.right()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.upCrossRight()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.upCrossLeft()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.downCrossRight()),
                Arguments.arguments(redPalaceCenter, redPalaceCenter.downCrossLeft()),

                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.up()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.down()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.left()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.right()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.upCrossRight()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.upCrossLeft()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.downCrossRight()),
                Arguments.arguments(greenPlaceCenter, greenPlaceCenter.downCrossLeft())
        );
    }

    private static Stream<Arguments> movableDestinationsOfRedPalaceByNorthWestConner() {
        Position northWest = Position.of(0, 3);
        return Stream.of(
                Arguments.arguments(northWest, northWest.downCrossRight()),
                Arguments.arguments(northWest, northWest.downCrossRight().downCrossRight()),
                Arguments.arguments(northWest, northWest.down()),
                Arguments.arguments(northWest, northWest.down().down()),
                Arguments.arguments(northWest, northWest.right()),
                Arguments.arguments(northWest, northWest.right().right())

        );
    }

    private static Stream<Arguments> nonMovableDestinationsOfRedPalaceByNorthWestConner() {
        Position northWest = Position.of(0, 3);
        return Stream.of(
                Arguments.arguments(northWest, northWest.downCrossLeft()),
                Arguments.arguments(northWest, northWest.downCrossLeft().downCrossLeft()),
                Arguments.arguments(northWest, northWest.downCrossRight().downCrossRight().downCrossRight())
        );
    }

    private static Stream<Arguments> nonMovableConnerDestinationsOfRedPalaceByBlocked() {
        Position redNorthEast = Position.of(0, 5);
        Position redNorthWest = Position.of(0,3);
        Position redSouthEast = Position.of(2,5);
        Position redSouthWest = Position.of(2,3);
        return Stream.of(
                Arguments.arguments(redNorthEast, redNorthEast.downCrossLeft().downCrossLeft()),
                Arguments.arguments(redNorthWest, redNorthWest.downCrossRight().downCrossRight()),
                Arguments.arguments(redSouthEast, redSouthEast.upCrossLeft().upCrossLeft()),
                Arguments.arguments(redSouthWest, redSouthWest.upCrossRight().upCrossRight())
        );
    }
}
