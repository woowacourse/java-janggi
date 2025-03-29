package domain.board;

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

        @DisplayName("오프셋만큼 이동한 위치를 반환한다.")
        @Test
        void calculatePosition() {
            // given
            BoardPosition from = new BoardPosition(3, 4);
            Offset offset = new Offset(2, -1);

            // when
            BoardPosition result = from.calculatePosition(offset);

            // then
            assertThat(result).isEqualTo(new BoardPosition(5, 3));
        }

        @DisplayName("위치 사이의 거리를 계산한다.")
        @Test
        void calculateOffset() {
            // given
            BoardPosition source = new BoardPosition(0, 0);
            BoardPosition after = new BoardPosition(1, 2);

            // when
            Offset result = after.calculateOffset(source);

            // then
            assertThat(result).isEqualTo(new Offset(1, 2));
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
