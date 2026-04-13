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

    private static final Position PALACE_RED_CENTER = new Position(1, 4);
    private static final Position PALACE_GREEN_CENTER = new Position(8, 4);

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
    void current_position_isPalaceGreenArea_false_test() {
        Palace palace = new Palace();
        Position currentPosition = new Position(4, 4);

        assertThat(palace.isPalaceGreenArea(currentPosition)).isFalse();
    }

    @Test
    @DisplayName("궁성의 중앙 위치에서 갈 수 있는 대각 위치를 반환해 준다. (궁성의 중앙은 각 모서리로 이동할 수 있다.)")
    void return_movable_positions_within_palace() {
        Palace palace = new Palace();

        List<Position> movablePositions = List.of(PALACE_RED_CENTER.upCrossRight(), PALACE_RED_CENTER.upCrossLeft(),
                PALACE_RED_CENTER.downCrossLeft(), PALACE_RED_CENTER.downCrossRight());

        assertThat(palace.reachablePositionsInPalace(PALACE_RED_CENTER)).containsExactlyElementsOf(movablePositions);
    }

    @ParameterizedTest
    @MethodSource("palaceRedCornerPosition")
    @DisplayName("궁성의 각 모서리는 궁성 중앙으로 이동할 수 있다. (궁성의 각 모서리는 중앙으로 이동할 수 있다.)")
    void return_movable_positions_within_palace(Position cornerPosition) {
        Palace palace = new Palace();

        assertThat(palace.reachablePositionsInPalace(cornerPosition)).containsExactly(PALACE_RED_CENTER);
    }

    private static Stream<Arguments> palaceRedArea() {
        return Stream.of(
                Arguments.arguments(PALACE_RED_CENTER.up()),
                Arguments.arguments(PALACE_RED_CENTER.upCrossLeft()),
                Arguments.arguments(PALACE_RED_CENTER.upCrossRight()),
                Arguments.arguments(PALACE_RED_CENTER.down()),
                Arguments.arguments(PALACE_RED_CENTER.downCrossLeft()),
                Arguments.arguments(PALACE_RED_CENTER.downCrossRight()),
                Arguments.arguments(PALACE_RED_CENTER.left()),
                Arguments.arguments(PALACE_RED_CENTER.right())
        );
    }

    private static Stream<Arguments> palaceGreenArea() {
        return Stream.of(
                Arguments.arguments(PALACE_GREEN_CENTER.up()),
                Arguments.arguments(PALACE_GREEN_CENTER.upCrossLeft()),
                Arguments.arguments(PALACE_GREEN_CENTER.upCrossRight()),
                Arguments.arguments(PALACE_GREEN_CENTER.down()),
                Arguments.arguments(PALACE_GREEN_CENTER.downCrossLeft()),
                Arguments.arguments(PALACE_GREEN_CENTER.downCrossRight()),
                Arguments.arguments(PALACE_GREEN_CENTER.left()),
                Arguments.arguments(PALACE_GREEN_CENTER.right())
        );
    }

    private static Stream<Arguments> palaceRedCornerPosition() {
        return Stream.of(
                Arguments.arguments(PALACE_RED_CENTER.upCrossLeft()),
                Arguments.arguments(PALACE_RED_CENTER.upCrossRight()),
                Arguments.arguments(PALACE_RED_CENTER.downCrossLeft()),
                Arguments.arguments(PALACE_RED_CENTER.downCrossRight())
        );
    }

}
