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

class CannonMoveStrategyTest {

    private static final Position PALACE_RED_CENTER = new Position(1, 4);
    private static final Position PALACE_GREEN_CENTER = new Position(8, 4);

    @Test
    @DisplayName("포 기물은 현재 위치 기준 상하좌우 방향으로 바로 1칸을 제외한 나머지 위치로 이동할 수 있어야 한다.")
    void cannon_can_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(9, 4);
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @Test
    @DisplayName("포 기물의 현재 위치 기준 상하좌우 방향으로 바로 1칸은 이동할 수 없다.")
    void cannon_cannot_move_test() {
        Position current = new Position(4, 4);
        Position destination = new Position(4, 5);
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("moveableWithInRedPalaceDiagonalPositions")
    @DisplayName("포 기물이 레드팀 궁성 영역의 모서리인 경우, 반대편 대각선 모서리로 이동할 수 있다. ex) 0,3 -> 2,5 이동 가능")
    void cannon_within_red_palace_corner_can_move_opposite_diagonal_corner_test(Position current, Position destination) {
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("moveableWithInGreenPalaceDiagonalPositions")
    @DisplayName("포 기물이 그린팀 궁성 영역의 모서리인 경우, 반대편 대각선 모서리로 이동할 수 있다. ex) 7,3 -> 9,5 이동 가능")
    void cannon_within_green_palace_corner_can_move_opposite_diagonal_corner_test(Position current, Position destination) {
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.canMoveTo(current, destination)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validPath")
    @DisplayName("포 기물은 목적지로 이동하는 경로에 기물이 단 하나만 포함되어야 이동할 수 있다.")
    void cannon_can_move_hasValidPathTo_valid_path_test(Position current, Position destination, List<Position> obstacles) {
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nonValidPath")
    @DisplayName("포 기물은 목적지로 이동하는 경로에 기물이 2개 이상이거나 없다면 이동할 수 없다.")
    void cannon_cannot_move_hasValidPathTo_invalid_path_test(Position current, Position destination, List<Position> obstacles) {
        CannonMoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

        assertThat(moveStrategy.hasValidPathTo(current, destination, obstacles)).isFalse();
    }

    private static Stream<Arguments> moveableWithInRedPalaceDiagonalPositions() {
        Position redPalaceUpLeftCorner = PALACE_RED_CENTER.upCrossLeft();
        Position redPalaceUpRightCorner = PALACE_RED_CENTER.upCrossRight();
        Position redPalaceDownLeftCorner = PALACE_RED_CENTER.downCrossLeft();
        Position redPalaceDownRightCorner = PALACE_RED_CENTER.downCrossRight();

        return Stream.of(Arguments.arguments(redPalaceUpLeftCorner, redPalaceUpLeftCorner.downCrossRight().downCrossRight()),
                Arguments.arguments(redPalaceUpRightCorner, redPalaceUpRightCorner.downCrossLeft().downCrossLeft()),
                Arguments.arguments(redPalaceDownLeftCorner, redPalaceDownLeftCorner.upCrossRight().upCrossRight()),
                Arguments.arguments(redPalaceDownRightCorner, redPalaceDownRightCorner.upCrossLeft().upCrossLeft()));
    }

    private static Stream<Arguments> moveableWithInGreenPalaceDiagonalPositions() {
        Position greenPalaceUpLeftCorner = PALACE_GREEN_CENTER.upCrossLeft();
        Position greenPalaceUpRightCorner = PALACE_GREEN_CENTER.upCrossRight();
        Position greenPalaceDownLeftCorner = PALACE_GREEN_CENTER.downCrossLeft();
        Position greenPalaceDownRightCorner = PALACE_GREEN_CENTER.downCrossRight();

        return Stream.of(Arguments.arguments(greenPalaceUpLeftCorner, greenPalaceUpLeftCorner.downCrossRight().downCrossRight()),
                Arguments.arguments(greenPalaceUpRightCorner, greenPalaceUpRightCorner.downCrossLeft().downCrossLeft()),
                Arguments.arguments(greenPalaceDownLeftCorner, greenPalaceDownLeftCorner.upCrossRight().upCrossRight()),
                Arguments.arguments(greenPalaceDownRightCorner, greenPalaceDownRightCorner.upCrossLeft().upCrossLeft()));
    }

    private static Stream<Arguments> validPath() {
        Position basicCurrent = new Position(4, 4);

        Position basicColMoveDestination = basicCurrent.right().right().right().right().right();
        Position obstacleColMove = basicCurrent.right().right().right();

        Position basicRowMoveDestination = basicCurrent.up().up().up().up().up();
        Position obstacleRowMove = basicCurrent.up().up().up();

        Position greenPalaceUpLeftCornerCurrentPosition = PALACE_GREEN_CENTER.upCrossLeft();
        Position greenPalaceDownRightCornerDestination = PALACE_GREEN_CENTER.downCrossRight();

        Position redPalaceUpLeftCornerCurrentPosition = PALACE_RED_CENTER.upCrossLeft();
        Position redPalaceDownRightCornerDestination = PALACE_RED_CENTER.downCrossRight();

        return Stream.of(Arguments.arguments(basicCurrent, basicColMoveDestination, List.of(obstacleColMove)),
                Arguments.arguments(basicCurrent, basicRowMoveDestination, List.of(obstacleRowMove)),
                Arguments.arguments(greenPalaceUpLeftCornerCurrentPosition, greenPalaceDownRightCornerDestination,
                        List.of(PALACE_GREEN_CENTER)),
                Arguments.arguments(redPalaceUpLeftCornerCurrentPosition, redPalaceDownRightCornerDestination,
                        List.of(PALACE_RED_CENTER)));
    }

    private static Stream<Arguments> nonValidPath() {
        Position basicCurrent = new Position(4, 4);

        Position basicColDestination = basicCurrent.right().right().right().right().right();
        Position obstacleColMoveOne = basicCurrent.right().right().right();
        Position obstacleColMoveTwo = basicCurrent.right().right();

        Position basicRowDestination = basicCurrent.up().up().up().up().up();
        Position obstacleRowMoveOne = basicCurrent.up().up().up();
        Position obstacleRowMoveTwo = basicCurrent.up().up();

        Position greenPalaceUpLeftCornerCurrentPosition = PALACE_GREEN_CENTER.upCrossLeft();
        Position greenPalaceDownRightCornerDestination = PALACE_GREEN_CENTER.downCrossRight();

        Position redPalaceUpLeftCornerCurrentPosition = PALACE_RED_CENTER.upCrossLeft();
        Position redPalaceDownRightCornerDestination = PALACE_RED_CENTER.downCrossRight();

        return Stream.of(Arguments.arguments(basicCurrent, basicColDestination, List.of(obstacleColMoveOne, obstacleColMoveTwo)),
                Arguments.arguments(basicCurrent, basicRowDestination, List.of(obstacleRowMoveOne, obstacleRowMoveTwo)),
                Arguments.arguments(greenPalaceUpLeftCornerCurrentPosition, greenPalaceDownRightCornerDestination,
                        List.of()),
                Arguments.arguments(redPalaceUpLeftCornerCurrentPosition, redPalaceDownRightCornerDestination,
                        List.of()));
    }
}
