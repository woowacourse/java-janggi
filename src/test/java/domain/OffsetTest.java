package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OffsetTest {

    @Nested
    class ValidCases {

        @DisplayName("움직임이 없는 오프셋인지 확인한다.")
        @Test
        void hasNoMovement() {
            assertAll(
                    () -> assertThat(new Offset(0, 0).hasNoMovement()).isTrue(),
                    () -> assertThat(new Offset(1, 0).hasNoMovement()).isFalse()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("오프셋의 범위를 벗어나면 예외가 발생한다.")
        @ParameterizedTest
        @CsvSource(
                value = {
                        "+9, 0",
                        "-9, 0",
                        "0, +10",
                        "0, -10"
                }
        )
        void validateRange(
                int x,
                int y
        ) {
            // when & then
            assertThatThrownBy(() -> new Offset(x, y))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("오프셋의 범위를 벗어났습니다.");
        }
    }
}
