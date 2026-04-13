package domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotMoveStrategyTest {

    private static final Position palaceRedCenter = new Position(1, 4);
    private static final Position palaceGreenCenter = new Position(8, 4);

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 내에 위치로 이동할 수 있어야 한다.")
    void chariot_can_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(9, 4);
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithInRedPalaceDiagonalPositions")
    @DisplayName("차 기물이 궁성 영역의 모서리인 경우, 반대편 대각선 모서리에도 이동할 수 있다. ex) 0,3 -> 1,4 -> 2,5 이동 가능")
    void chariot_within_red_palace_corner_can_move_opposite_diagonal_corner_test(Position current, Position destination) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithInGreenPalaceDiagonalPositions")
    @DisplayName("차 기물이 궁성 영역의 모서리인 경우, 반대편 대각선 모서리에도 이동할 수 있다. ex) 7,3 -> 8,4 -> 9,5 이동 가능")
    void chariot_within_green_palace_corner_can_move_opposite_diagonal_corner_test(Position current, Position destination) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 제자리 이동이 불가능하다.")
    void chariot_same_position_cannot_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(4, 4);
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 현재 위치 기준 모든 상하좌우 범위 이동 이외에 이동할 수 없어야 한다.")
    void chariot_cannot_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(9, 5);
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("blockedRoute")
    @DisplayName("차 기물은 목적지로 이동하는 경로에 기물이 있다면 이동할 수 없다.")
    void chariot_hasValidPathTo_blocked_route_cannot_move_test(Position current, Position destination, List<Position> obstacles) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("nonBlockedRoute")
    @DisplayName("차 기물은 목적지로 이동하는 경로에 기물이 없다면 이동할 수 있다.")
    void chariot_chasValidPathTo_non_blocked_route_an_move_test(Position current, Position destination, List<Position> obstacles) {
        ChariotMoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isTrue();
    }

    private static Stream<Arguments> moveableWithInRedPalaceDiagonalPositions() {
        Position redPalaceUpLeftCorner = palaceRedCenter.upCrossLeft();
        Position redPalaceUpRightCorner = palaceRedCenter.upCrossRight();
        Position redPalaceDownLeftCorner = palaceRedCenter.downCrossLeft();
        Position redPalaceDownRightCorner = palaceRedCenter.downCrossRight();

        return Stream.of(Arguments.arguments(redPalaceUpLeftCorner, redPalaceUpLeftCorner.downCrossRight().downCrossRight()),
                Arguments.arguments(redPalaceUpRightCorner, redPalaceUpRightCorner.downCrossLeft().downCrossLeft()),
                Arguments.arguments(redPalaceDownLeftCorner, redPalaceDownLeftCorner.upCrossRight().upCrossRight()),
                Arguments.arguments(redPalaceDownRightCorner, redPalaceDownRightCorner.upCrossLeft().upCrossLeft()),
                Arguments.arguments(redPalaceDownRightCorner, palaceRedCenter),
                Arguments.arguments(palaceRedCenter, palaceRedCenter.left()));
    }

    private static Stream<Arguments> moveableWithInGreenPalaceDiagonalPositions() {
        Position greenPalaceUpLeftCorner = palaceGreenCenter.upCrossLeft();
        Position greenPalaceUpRightCorner = palaceGreenCenter.upCrossRight();
        Position greenPalaceDownLeftCorner = palaceGreenCenter.downCrossLeft();
        Position greenPalaceDownRightCorner = palaceGreenCenter.downCrossRight();

        return Stream.of(Arguments.arguments(greenPalaceUpLeftCorner, greenPalaceUpLeftCorner.downCrossRight().downCrossRight()),
                Arguments.arguments(greenPalaceUpRightCorner, greenPalaceUpRightCorner.downCrossLeft().downCrossLeft()),
                Arguments.arguments(greenPalaceDownLeftCorner, greenPalaceDownLeftCorner.upCrossRight().upCrossRight()),
                Arguments.arguments(greenPalaceDownRightCorner, greenPalaceDownRightCorner.upCrossLeft().upCrossLeft()),
                Arguments.arguments(greenPalaceDownRightCorner, palaceGreenCenter),
                Arguments.arguments(palaceGreenCenter, palaceGreenCenter.left()));
    }

    private static Stream<Arguments> blockedRoute() {
        Position basicCurrent = new Position(4, 4);
        Position basicDestination = basicCurrent.right().right().right().right().right();
        Position obstacle = basicCurrent.right().right().right();

        Position greenPalaceUpLeftCornerCurrentPosition = palaceGreenCenter.upCrossLeft();
        Position greenPalaceDownRightCornerDestination = palaceGreenCenter.downCrossRight();

        Position redPalaceUpLeftCornerCurrentPosition = palaceRedCenter.upCrossLeft();
        Position redPalaceDownRightCornerDestination = palaceRedCenter.downCrossRight();

        return Stream.of(Arguments.arguments(basicCurrent, basicDestination, List.of(obstacle)),
                Arguments.arguments(greenPalaceUpLeftCornerCurrentPosition, greenPalaceDownRightCornerDestination,
                        List.of(palaceGreenCenter)),
                Arguments.arguments(redPalaceUpLeftCornerCurrentPosition, redPalaceDownRightCornerDestination,
                        List.of(palaceRedCenter)));
    }

    private static Stream<Arguments> nonBlockedRoute() {
        Position basicCurrent = new Position(4, 4);
        Position basicDestination = new Position(4, 9);

        Position greenPalaceUpLeftCornerCurrentPosition = palaceGreenCenter.upCrossLeft();
        Position greenPalaceDownRightCornerDestination = palaceGreenCenter.downCrossRight();

        Position redPalaceUpLeftCornerCurrentPosition = palaceRedCenter.upCrossLeft();
        Position redPalaceDownRightCornerDestination = palaceRedCenter.downCrossRight();

        return Stream.of(Arguments.arguments(basicCurrent, basicDestination, List.of()),
                Arguments.arguments(greenPalaceUpLeftCornerCurrentPosition, greenPalaceDownRightCornerDestination,
                        List.of()),
                Arguments.arguments(redPalaceUpLeftCornerCurrentPosition, redPalaceDownRightCornerDestination,
                        List.of()),
                Arguments.arguments(redPalaceUpLeftCornerCurrentPosition, palaceRedCenter,
                        List.of()));
    }

}
