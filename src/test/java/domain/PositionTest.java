package domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @ParameterizedTest
    @MethodSource("palaceRedArea")
    @DisplayName("현재 위치가 한나라(Red) 궁성 영역이면 true를 반환한다.")
    void current_position_isPalaceRedArea_true_test(Position currentPosition) {
        Assertions.assertThat(currentPosition.isPalaceRedArea()).isTrue();
    }

    @Test
    @DisplayName("현재 위치가 한나라(Red) 궁성 영역이 아니라면 false를 반환한다.")
    void current_position_isPalaceRedArea_false_test() {
        Position currentPosition = new Position(4, 4);

        Assertions.assertThat(currentPosition.isPalaceRedArea()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("palaceGreenArea")
    @DisplayName("현재 위치가 초나라(Green) 궁성 영역이면 true를 반환한다.")
    void current_position_isPalaceGreenArea_true_test(Position currentPosition) {
        Assertions.assertThat(currentPosition.isPalaceGreenArea()).isTrue();
    }

    @Test
    @DisplayName("현재 위치가 초나라(Green) 궁성 영역이면 false를 반환한다.")
    void current_position_isPalaceGreenArea_true_test() {
        Position currentPosition = new Position(4, 4);

        Assertions.assertThat(currentPosition.isPalaceGreenArea()).isFalse();
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
}
