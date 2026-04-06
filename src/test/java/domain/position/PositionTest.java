package domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 경계값_최소_범위의_위치를_생성한다() {
        Position position = new Position(1, 1);

        assertThat(position).isNotNull();
    }

    @Test
    void 경계값_최대_범위의_위치를_생성한다() {
        Position position = new Position(10, 9);

        assertThat(position).isNotNull();
    }

    @Test
    void 행이_0이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(0, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("행의 위치는");
    }

    @Test
    void 행이_11이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(11, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("행의 위치는");
    }

    @Test
    void 열이_0이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(5, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("열의 위치는");
    }

    @Test
    void 열이_11이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(5, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("열의 위치는");
    }

    @Test
    void 행과_열이_모두_음수이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Position(-1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("행의 위치는");
    }

    @Test
    void 같은_행이면_true를_반환한다() {
        Position position = new Position(1, 5);
        Position otherPosition = new Position(1, 3);

        assertThat(position.isSameRow(otherPosition)).isTrue();
    }

    @Test
    void 다른_행이면_false를_반환한다() {
        Position position = new Position(1, 5);
        Position otherPosition = new Position(2, 5);

        assertThat(position.isSameRow(otherPosition)).isFalse();
    }

    @Test
    void 같은_열이면_true를_반환한다() {
        Position position = new Position(1, 5);
        Position otherPosition = new Position(3, 5);

        assertThat(position.isSameColumn(otherPosition)).isTrue();
    }

    @Test
    void 다른_열이면_false를_반환한다() {
        Position position = new Position(1, 5);
        Position otherPosition = new Position(1, 6);

        assertThat(position.isSameColumn(otherPosition)).isFalse();
    }
}
