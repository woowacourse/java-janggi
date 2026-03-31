package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PositionTest {
    @ParameterizedTest
    @DisplayName("x좌표와 y좌표의 범위가 잘못된 경우 예외가 발생한다.")
    @MethodSource("invalidPositionRange")
    void invalidRangeExceptionTest(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] x좌표와 y좌표의 범위가 올바르지 않습니다.");
    }

    static Stream<Arguments> invalidPositionRange() {
        return Stream.of(
                Arguments.arguments(-1, -1),
                Arguments.arguments(8, 10),
                Arguments.arguments(9, 9),
                Arguments.arguments(10, 10)
        );
    }
}
