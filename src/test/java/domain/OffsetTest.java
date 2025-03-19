package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OffsetTest {

    @Nested
    class ValidCases {

        @DisplayName("오프셋의 합을 계산한다.")
        @Test
        void calculateSum() {
            // given
            List<Offset> offsets = List.of(new Offset(1, 2), new Offset(4, -3));

            // when
            Offset offset = Offset.calculateSum(offsets);

            // then
            assertThat(offset).isEqualTo(new Offset(5, -1));
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
