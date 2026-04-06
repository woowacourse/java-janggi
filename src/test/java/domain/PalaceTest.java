package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    @ParameterizedTest
    @MethodSource("palaceRedArea")
    @DisplayName("현재 위치가 한나라(Red) 궁성 영역이면 true를 반환한다.")
    void current_position_isPalaceRedArea_true_test(Position currentPosition) {
        Palace palace = new Palace();

        assertThat(palace.isPalaceRedArea(currentPosition)).isTrue();
    }

    @Test
    @DisplayName("현재 위치가 한나라(Red) 궁성 영역이 아니라면 false를 반환한다.")
    void current_position_isPalaceRedArea_false_test() {
        Palace palace = new Palace();
        Position currentPosition = new Position(4, 4);

        assertThat(palace.isPalaceRedArea(currentPosition)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("palaceGreenArea")
    @DisplayName("현재 위치가 초나라(Green) 궁성 영역이면 true를 반환한다.")
    void current_position_isPalaceGreenArea_true_test(Position currentPosition) {
        Palace palace = new Palace();

        assertThat(palace.isPalaceGreenArea(currentPosition)).isTrue();
    }

    @Test
    @DisplayName("현재 위치가 초나라(Green) 궁성 영역이면 false를 반환한다.")
    void current_position_isPalaceGreenArea_true_test() {
        Palace palace = new Palace();
        Position currentPosition = new Position(4, 4);

        assertThat(palace.isPalaceGreenArea(currentPosition)).isFalse();
    }

    @Test
    @DisplayName("궁성의 중앙 위치에서 갈 수 있는 대각 위치를 반환해 준다. (궁성의 중앙은 각 모서리로 이동할 수 있다.)")
    void return_movable_positions_within_palace() {
        Palace palace = new Palace();
        Position currentPosition = new Position(1, 4);

        List<Position> movablePositions = List.of(currentPosition.upCrossRight(), currentPosition.upCrossLeft(),
                currentPosition.downCrossLeft(), currentPosition.downCrossRight());

        assertThat(palace.reachablePositionsInPalace(currentPosition)).containsExactlyElementsOf(movablePositions);
    }

    @ParameterizedTest
    @MethodSource("palaceRedCornerPosition")
    @DisplayName("궁성의 각 모서리는 궁성 중앙으로 이동할 수 있다. (궁성의 각 모서리는 중앙으로 이동할 수 있다.)")
    void return_movable_positions_within_palace(Position cornerPosition) {
        Palace palace = new Palace();

        assertThat(palace.reachablePositionsInPalace(cornerPosition)).containsExactly(new Position(1, 4));
    }

    private static Stream<Arguments> palaceRedArea() {
        Position palaceRedCenter = new Position(1, 4);
        return Stream.of(
                Arguments.arguments(palaceRedCenter.up()),
                Arguments.arguments(palaceRedCenter.upCrossLeft()),
                Arguments.arguments(palaceRedCenter.upCrossRight()),
                Arguments.arguments(palaceRedCenter.down()),
                Arguments.arguments(palaceRedCenter.downCrossLeft()),
                Arguments.arguments(palaceRedCenter.downCrossRight()),
                Arguments.arguments(palaceRedCenter.left()),
                Arguments.arguments(palaceRedCenter.right())
        );
    }

    private static Stream<Arguments> palaceGreenArea() {
        Position palaceRedCenter = new Position(8, 4);
        return Stream.of(
                Arguments.arguments(palaceRedCenter.up()),
                Arguments.arguments(palaceRedCenter.upCrossLeft()),
                Arguments.arguments(palaceRedCenter.upCrossRight()),
                Arguments.arguments(palaceRedCenter.down()),
                Arguments.arguments(palaceRedCenter.downCrossLeft()),
                Arguments.arguments(palaceRedCenter.downCrossRight()),
                Arguments.arguments(palaceRedCenter.left()),
                Arguments.arguments(palaceRedCenter.right())
        );
    }

    private static Stream<Arguments> palaceRedCornerPosition() {
        Position palaceRedCenter = new Position(1, 4);
        return Stream.of(
                Arguments.arguments(palaceRedCenter.upCrossLeft()),
                Arguments.arguments(palaceRedCenter.upCrossRight()),
                Arguments.arguments(palaceRedCenter.downCrossLeft()),
                Arguments.arguments(palaceRedCenter.downCrossRight())
        );
    }

}
