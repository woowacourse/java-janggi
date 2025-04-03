package janggi.rule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.value.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardPositionRangeTest {

    @DisplayName("정해진 보드의 좌표 내에 있는 위치값인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canCheckInRange(Position position) {
        BoardPositionRange.validateInRange(position);
    }

    static Stream<Arguments> canCheckInRange() {
        return Stream.of(
                Arguments.of(new Position(0, 0)),
                Arguments.of(new Position(8, 0)),
                Arguments.of(new Position(0, 9)),
                Arguments.of(new Position(8, 9))
        );
    }

    @DisplayName("전해진 보드 좌표를 벗어난 위치값인지 확인할 수 있다.")
    @ParameterizedTest
    @MethodSource()
    void canCheckOutRange(Position position) {
        assertThatThrownBy(() -> BoardPositionRange.validateInRange(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
    }


    static Stream<Arguments> canCheckOutRange() {
        return Stream.of(
                Arguments.of(new Position(-1, 0), false),
                Arguments.of(new Position(9, 0), false),
                Arguments.of(new Position(0, -1), false),
                Arguments.of(new Position(0, 10), false)
        );
    }

}