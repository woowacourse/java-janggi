package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RowTest {

    @Nested
    class 생성 {
        @Test
        void 유효한_값으로_행_객체가_생성된_경우() {
            int input = 3;

            Row row = new Row(input);

            assertThat(row.value()).isEqualTo(3);
        }
    }

    @Nested
    class 예외 {
        @Test
        void 값이_최댓값을_초과하면_예외를_발행한다() {
            int input = 19;

            assertThatThrownBy(() -> new Row(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("행의 최대 값은 8입니다.");
        }
    }
}
