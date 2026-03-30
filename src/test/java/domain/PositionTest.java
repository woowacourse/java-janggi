package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class PositionTest {

    @Nested
    class 좌표_범위_검증_테스트 {
        @Test
        void x좌표가_0_미만일_경우_예외를_던진다() {
            Assertions.assertThatThrownBy(() -> new Position(9, 2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void x좌표가_8을_초과할_경우_예외를_던진다() {
            Assertions.assertThatThrownBy(() -> new Position(-1, 2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void y좌표가_0_미만일_경우_예외를_던진다() {
            Assertions.assertThatThrownBy(() -> new Position(3, -1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void y좌표가_9를_초과할_경우_예외를_던진다() {
            Assertions.assertThatThrownBy(() -> new Position(2, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
