package domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PositionTest {

    @Nested
    class 좌표_범위_검증_테스트 {
        @Test
        void x좌표가_0_미만일_경우_예외를_던진다() {
            assertThatThrownBy(() -> new Position(9, 2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void x좌표가_8을_초과할_경우_예외를_던진다() {
            assertThatThrownBy(() -> new Position(-1, 2))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void y좌표가_0_미만일_경우_예외를_던진다() {
            assertThatThrownBy(() -> new Position(3, -1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void y좌표가_9를_초과할_경우_예외를_던진다() {
            assertThatThrownBy(() -> new Position(2, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 좌표_차이_계산_테스트 {
        Position position = new Position(0, 0);

        @Test
        void 두_좌표의_x_증가량을_계산한다() {
            assertThat(position.calculateDeltaX(new Position(1,0))).isEqualTo(1);
        }

        @Test
        void 두_좌표의_y_증가량을_계산한다() {
            assertThat(position.calculateDeltaY(new Position(0,1))).isEqualTo(1);

        }
    }

}
