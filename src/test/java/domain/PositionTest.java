package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 경계값_최대_범위의_위치를_생성한다() {
        Position position = new Position(9, 10);

        assertThat(position).isNotNull();
    }

    @Test
    void 행이_0이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(0, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 행이_10이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(10, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 열이_0이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(5, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 열이_11이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(5, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 행과_열이_모두_음수이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(-1, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
