package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardPositionTest {

    @Nested
    class ValidCases {

        @DisplayName("위치 사이의 거리를 계산한다.")
        @Test
        void calculateOffset() {
            // given
            BoardPosition before = new BoardPosition(0, 0);
            BoardPosition after = new BoardPosition(1, 2);

            // when
            Offset offset = after.calculateOffset(before);

            // then
            assertThat(offset).isEqualTo(new Offset(1, 2));
        }
    }

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
            assertThatThrownBy(() -> new BoardPosition(x, y))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("장기판의 범위를 벗어났습니다.");
        }
    }
}
