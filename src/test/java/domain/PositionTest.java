package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @Nested
    class InvalidCases {

        @DisplayName("포지션의 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource(
            value = {
                "-1, 0",
                "9, 0",
                "0, -1",
                "0, 10"
            }
        )
        void validateRange(
            int x,
            int y
        ) {
            // when & then
            assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판의 범위를 벗어났습니다.");
        }
    }
}
