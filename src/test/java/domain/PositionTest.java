package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Nested
    class 생성 {
        @Test
        void 같은_좌표를_요청하면_캐시된_객체를_재사용한다() {
            final Position first = Position.of(3, 4);
            final Position second = Position.of(3, 4);

            assertThat(first).isSameAs(second);
        }
    }

    @Nested
    class 예외 {
        @Test
        void 행이_범위를_벗어나면_예외를_발생한다() {
            assertThatThrownBy(() -> Position.of(-1, 4))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("행의 최소 값은 0입니다.");
        }

        @Test
        void 열이_범위를_벗어나면_예외를_발생한다() {
            assertThatThrownBy(() -> Position.of(4, 9))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("열의 최대 값은 8입니다.");
        }
    }
}
