package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GreenSoldierMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸 이동할 수 있다.")
    void soldier_move_test(Position greenSoldierPosition, Position expectedTarget) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovablePositions")
    @DisplayName("초나라 졸은 현재 위치 기준 상, 좌우 한 칸을 벗어난 곳으로 이동할 수 없다.")
    void soldier_move_test_negative(Position greenSoldierPosition, Position wrongTarget) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfRedPalaceWestSide")
    @DisplayName("초나라 졸은 한나라 궁성 면에서는 대각선으로 이동할 수 없어야 한다.")
    void palace_piece_side_move_test_negative(Position greenSoldierPosition, Position wrongTarget) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableCrossDirectionsOutOfRedPalace")
    @DisplayName("초나라 졸은 한나라 궁 외에서 대각선으로 이동할 수 없어야 한다.")
    void palace_piece_out_of_palace(Position greenSoldierPosition, Position wrongTarget) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfRedPalaceSouthWestConner")
    @DisplayName("초나라 졸은 한나라 궁 내부 남서귀에서 동, 서, 북, 북동 쪽으로 이동할 수 있다.")
    void palace_piece_move_south_west_conner_test(Position greenSoldierPosition, Position expectedPosition) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfRedPalaceSouthEastConner")
    @DisplayName("초나라 졸은 한나라 궁 내부 남동귀에서 동, 서, 북, 북서 쪽으로 이동할 수 있다.")
    void palace_piece_move_south_east_conner_test(Position greenSoldierPosition, Position expectedPosition) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfRedPalaceNorthWestConner")
    @DisplayName("초나라 졸은 한나라 궁 내부 북서귀에서 동서 방향을 제외한 방향으로 이동할 수 없다.")
    void palace_piece_move_north_west_conner_test_negative(Position greenSoldierPosition, Position wrongTarget) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfRedPalaceCenter")
    @DisplayName("초나라 졸은 한나라 궁 내부 중앙에서 서, 서북, 북, 북동, 동 방향으로 이동할 수 있어야 한다.")
    void palace_piece_move_center_test(Position greenSoldierPosition, Position expectedPosition) {
        GreenSoldierMoveStrategy moveStrategy = GreenSoldierMoveStrategy.of(greenSoldierPosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
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

    private static Stream<Arguments> nonMovableDirectionsOfRedPalaceWestSide() {
        Position westSide = Position.of(1,3);
        return Stream.of(
                Arguments.arguments(westSide, westSide.upCrossLeft()),
                Arguments.arguments(westSide, westSide.upCrossRight())
        );
    }

    private static Stream<Arguments> nonMovableCrossDirectionsOutOfRedPalace() {
        Position nonRedPalace = Position.of(4,4);
        return Stream.of(
                Arguments.arguments(nonRedPalace, nonRedPalace.upCrossRight()),
                Arguments.arguments(nonRedPalace, nonRedPalace.upCrossLeft())
        );
    }

    private static Stream<Arguments> movableDirectionsOfRedPalaceSouthWestConner() {
        Position redSouthWestConner = Position.of(2, 3);
        return Stream.of(
                Arguments.arguments(redSouthWestConner, redSouthWestConner.right()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.left()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.up()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.upCrossRight())
        );
    }

    private static Stream<Arguments> movableDirectionsOfRedPalaceSouthEastConner() {
        Position redSouthWestConner = Position.of(2, 5);
        return Stream.of(
                Arguments.arguments(redSouthWestConner, redSouthWestConner.right()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.left()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.up()),
                Arguments.arguments(redSouthWestConner, redSouthWestConner.upCrossLeft())
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOfRedPalaceNorthWestConner() {
        Position northWestConner = Position.of(0, 3);
        return Stream.of(
                Arguments.arguments(northWestConner, northWestConner.down()),
                Arguments.arguments(northWestConner, northWestConner.downCrossLeft()),
                Arguments.arguments(northWestConner, northWestConner.downCrossRight())
        );
    }

    private static Stream<Arguments> movableDirectionsOfRedPalaceCenter() {
        Position center = Position.of(1,4);
        return Stream.of(
                Arguments.arguments(center, center.up()),
                Arguments.arguments(center, center.left()),
                Arguments.arguments(center, center.right()),
                Arguments.arguments(center, center.upCrossRight()),
                Arguments.arguments(center, center.upCrossLeft())
        );
    }
}
