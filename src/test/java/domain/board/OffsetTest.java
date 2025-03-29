package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OffsetTest {

    @Nested
    class ValidCases {

        @DisplayName("수평 또는 수직일 때 직선 여부를 판별할 수 있다.")
        @Test
        void isLinear() {
            // given
            Offset horizontal = new Offset(3, 0);
            Offset vertical = new Offset(0, -5);
            Offset diagonal = new Offset(3, 3);

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(horizontal.isLinear())
                    .isTrue();
                softly.assertThat(vertical.isLinear())
                    .isTrue();
                softly.assertThat(diagonal.isLinear())
                    .isFalse();
            });
        }

        @DisplayName("수평 오프셋일 때 단위 오프셋을 계산할 수 있다.")
        @Test
        void calculateLinearOffsets_horizontal() {
            // given
            Offset offset = new Offset(0, 3);

            // when
            List<Offset> result = offset.calculateLinearOffsets();

            // then
            assertThat(result).isEqualTo(List.of(
                new Offset(0, 1),
                new Offset(0, 1),
                new Offset(0, 1)
            ));
        }

        @DisplayName("수직 오프셋일 때 단위 오프셋을 계산할 수 있다.")
        @Test
        void calculateLinearOffsets_vertical() {
            // given
            Offset offset = new Offset(-4, 0);

            // when
            List<Offset> result = offset.calculateLinearOffsets();

            // then
            assertThat(result).isEqualTo(List.of(
                new Offset(-1, 0),
                new Offset(-1, 0),
                new Offset(-1, 0),
                new Offset(-1, 0)
            ));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("오프셋의 범위를 벗어날 수 없다.")
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

        @DisplayName("직선이 아닌 오프셋에서 단위 오프셋을 계산할 수 없다.")
        @Test
        void calculateLinearOffsets() {
            // given
            Offset offset = new Offset(1, 1);

            // when & then
            assertThatThrownBy(offset::calculateLinearOffsets)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 오프셋은 직선이 아닙니다.");
        }
    }
}
