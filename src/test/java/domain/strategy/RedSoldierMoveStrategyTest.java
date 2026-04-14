package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RedSoldierMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸 이동할 수 있다.")
    void soldier_move_test(Position RedSoldierPosition, Position expectedTarget) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("한나라 졸은 현재 위치 기준 하, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void soldier_move_test_negative(Position RedSoldierPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfGreenPalaceWestSide")
    @DisplayName("한나라 졸은 초나라 궁성 면에서는 대각선으로 이동할 수 없어야 한다.")
    void palace_piece_side_move_test_negative(Position RedSoldierPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableCrossDirectionsOutOfGreenPalace")
    @DisplayName("한나라 졸은 초나라 궁 외에서 대각선으로 이동할 수 없어야 한다.")
    void palace_piece_out_of_palace(Position RedSoldierPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfGreenPalaceNorthWestConner")
    @DisplayName("한나라 졸은 초나라 궁 내부 북서귀에서 동, 서, 남, 남동 쪽으로 이동할 수 있다.")
    void palace_piece_move_south_west_conner_test(Position RedSoldierPosition, Position expectedPosition) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfGreenPalaceNorthEastConner")
    @DisplayName("한나라 졸은 초나라 궁 내부 북동귀에서 동, 서, 남, 남서 쪽으로 이동할 수 있다.")
    void palace_piece_move_south_east_conner_test(Position RedSoldierPosition, Position expectedPosition) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfGreenPalaceSouthWestConner")
    @DisplayName("한나라 졸은 초나라 궁 내부 남서귀에서 동서 방향을 제외한 방향으로 이동할 수 없다.")
    void palace_piece_move_north_west_conner_test_negative(Position RedSoldierPosition, Position wrongTarget) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfGreenPalaceCenter")
    @DisplayName("한나라 졸은 초나라 궁 내부 중앙에서 서, 서남, 남, 남동, 동 방향으로 이동할 수 있어야 한다.")
    void palace_piece_move_center_test(Position RedSoldierPosition, Position expectedPosition) {
        RedSoldierMoveStrategy moveStrategy = RedSoldierMoveStrategy.of(RedSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(6, 2), Position.of(6, 1)),
                Arguments.arguments(Position.of(6, 2), Position.of(6, 3)),
                Arguments.arguments(Position.of(6, 2), Position.of(7, 2))
        );
    }

    private static Stream<Arguments> nonMovablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(6, 2), Position.of(6, 4)),
                Arguments.arguments(Position.of(6, 2), Position.of(1, 2)),
                Arguments.arguments(Position.of(6, 2), Position.of(5, 3)),

                Arguments.arguments(Position.of(6, 2), Position.of(5, 2)),
                Arguments.arguments(Position.of(6, 2), Position.of(8, 3))
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOfGreenPalaceWestSide() {
        Position westSide = Position.of(8,3);
        return Stream.of(
                Arguments.arguments(westSide, westSide.downCrossLeft()),
                Arguments.arguments(westSide, westSide.downCrossRight())
        );
    }

    private static Stream<Arguments> nonMovableCrossDirectionsOutOfGreenPalace() {
        Position nonRedPalace = Position.of(4,4);
        return Stream.of(
                Arguments.arguments(nonRedPalace, nonRedPalace.downCrossRight()),
                Arguments.arguments(nonRedPalace, nonRedPalace.downCrossLeft())
        );
    }

    private static Stream<Arguments> movableDirectionsOfGreenPalaceNorthWestConner() {
        Position greenNorthWest = Position.of(7, 3);
        return Stream.of(
                Arguments.arguments(greenNorthWest, greenNorthWest.right()),
                Arguments.arguments(greenNorthWest, greenNorthWest.left()),
                Arguments.arguments(greenNorthWest, greenNorthWest.down()),
                Arguments.arguments(greenNorthWest, greenNorthWest.downCrossRight())
        );
    }

    private static Stream<Arguments> movableDirectionsOfGreenPalaceNorthEastConner() {
        Position greenNorthEast = Position.of(7, 5);
        return Stream.of(
                Arguments.arguments(greenNorthEast, greenNorthEast.right()),
                Arguments.arguments(greenNorthEast, greenNorthEast.left()),
                Arguments.arguments(greenNorthEast, greenNorthEast.down()),
                Arguments.arguments(greenNorthEast, greenNorthEast.downCrossLeft())
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOfGreenPalaceSouthWestConner() {
        Position northWestConner = Position.of(7, 3);
        return Stream.of(
                Arguments.arguments(northWestConner, northWestConner.up()),
                Arguments.arguments(northWestConner, northWestConner.upCrossLeft()),
                Arguments.arguments(northWestConner, northWestConner.upCrossRight())
        );
    }

    private static Stream<Arguments> movableDirectionsOfGreenPalaceCenter() {
        Position center = Position.of(8,4);
        return Stream.of(
                Arguments.arguments(center, center.down()),
                Arguments.arguments(center, center.left()),
                Arguments.arguments(center, center.right()),
                Arguments.arguments(center, center.downCrossLeft()),
                Arguments.arguments(center, center.downCrossRight())
        );
    }
}
