package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalacePieceMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("moveablePositions")
    @DisplayName("장, 사는 현재 위치 기준 상하좌우 한 칸 이동할 수 있다.")
    void general_move_test(Position palacePiecePosition, Position expectedTarget) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(palacePiecePosition);

        assertThat(moveStrategy.isMoveAble(expectedTarget)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfWestSide")
    @DisplayName("장, 사는 면에서는 대각선으로 이동할 수 없어야 한다.")
    void palace_piece_side_move_test_negative(Position palacePiecePosition, Position wrongTarget) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(palacePiecePosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOutOfRange")
    @DisplayName("장, 사는 궁 외로 이동할 수 없어야 한다.")
    void palace_piece_out_of_palace(Position palacePiecePosition, Position wrongTarget) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(palacePiecePosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfNorthWestConner")
    @DisplayName("장,사는 현재 궁 내부 북서귀에서 동쪽, 남쪽, 동남쪽으로 이동할 수 있다.")
    void palace_piece_move_north_west_conner_test(Position palacePiecePosition, Position expectedPosition) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(palacePiecePosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonMovableDirectionsOfNorthWestConner")
    @DisplayName("장, 사는 현재 궁 내부 북서귀에서 동쪽, 남쪽, 동남쪽을 제외한 방향으로 이동할 수 없다.")
    void palace_piece_move_north_west_conner_test_negative(Position guardPosition, Position wrongTarget) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(guardPosition);

        assertThat(moveStrategy.isMoveAble(wrongTarget)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("movableDirectionsOfCenter")
    @DisplayName("장, 사는 현재 궁 내부 중앙에서 팔 방향으로 이동할 수 있어야 한다.")
    void palace_piece_move_center_test(Position palacePiecePosition, Position expectedPosition) {
        PalacePieceMoveStrategy moveStrategy = PalacePieceMoveStrategy.of(palacePiecePosition);

        assertThat(moveStrategy.isMoveAble(expectedPosition)).isTrue();
    }

    private static Stream<Arguments> moveablePositions() {
        return Stream.of(
                Arguments.arguments(Position.of(1, 4), Position.of(1,5)),
                Arguments.arguments(Position.of(1, 4), Position.of(1,3)),
                Arguments.arguments(Position.of(1, 4), Position.of(2,4)),
                Arguments.arguments(Position.of(1, 4), Position.of(0,4))
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOfWestSide() {
        Position westSide = Position.of(1,3);
        return Stream.of(
                Arguments.arguments(westSide, westSide.downCrossRight()),
                Arguments.arguments(westSide, westSide.upCrossRight())
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOutOfRange() {
        Position redWestSide = Position.of(1,3);
        Position redNorthWestConner = Position.of(0,3);

        return Stream.of(
                Arguments.arguments(redWestSide, redWestSide.downCrossLeft()),
                Arguments.arguments(redWestSide, redWestSide.left()),
                Arguments.arguments(redWestSide, redWestSide.upCrossLeft()),
                Arguments.arguments(redNorthWestConner, redNorthWestConner.left()),
                Arguments.arguments(redWestSide, redNorthWestConner.down()),
                Arguments.arguments(redWestSide, redNorthWestConner.downCrossLeft())
        );
    }

    private static Stream<Arguments> movableDirectionsOfNorthWestConner() {
        Position northWestConner = Position.of(0, 3);
        return Stream.of(
                Arguments.arguments(northWestConner, northWestConner.right()),
                Arguments.arguments(northWestConner, northWestConner.downCrossRight()),
                Arguments.arguments(northWestConner, northWestConner.down())
        );
    }

    private static Stream<Arguments> nonMovableDirectionsOfNorthWestConner() {
        Position northWestConner = Position.of(0, 3);
        return Stream.of(
                Arguments.arguments(northWestConner, northWestConner.left()),
                Arguments.arguments(northWestConner, northWestConner.downCrossLeft())
        );
    }

    private static Stream<Arguments> movableDirectionsOfCenter() {
        Position center = Position.of(1,4);
        return Stream.of(
                Arguments.arguments(center, center.up()),
                Arguments.arguments(center, center.down()),
                Arguments.arguments(center, center.left()),
                Arguments.arguments(center, center.right()),
                Arguments.arguments(center, center.upCrossRight()),
                Arguments.arguments(center, center.upCrossLeft()),
                Arguments.arguments(center, center.downCrossLeft()),
                Arguments.arguments(center, center.downCrossRight())
        );
    }
}