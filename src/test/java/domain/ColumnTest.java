package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Nested
    class 생성 {
        @Test
        void 유효한_값으로_행_객체가_생성된_경우() {
            int input = 3;

            Column column = new Column(input);

            assertThat(column.value()).isEqualTo(3);
        }
    }

    @Nested
    class 예외 {
        @Test
        void 값이_최댓값을_초과하면_예외를_발행한다() {
            int input = 19;

            assertThatThrownBy(() -> new Column(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("열의 최대 값은 9입니다.");
        }
    }
}
