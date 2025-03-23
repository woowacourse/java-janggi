package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {

    @DisplayName("움직임들을 결합하여 하나의 움직임으로 만들 수 있다.")
    @Test
    void test() {
        // given
        Movement left = Movement.LEFT;
        Movement down = Movement.DOWN;

        // when
        Movement combined = Movement.combine(left, down);

        // then
        assertAll(
            () -> assertThat(combined.deltaX()).isEqualTo(left.deltaX() + down.deltaX()),
            () -> assertThat(combined.deltaY()).isEqualTo(left.deltaY() + down.deltaY())
        );
    }

}
