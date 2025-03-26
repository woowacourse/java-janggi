package routes;

import static janggi.position.Row.NINE;
import static janggi.position.Row.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RowTest {
    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_1() {
        // given
        int step = 1;

        // when - then
        assertThat(ZERO.canMove(step)).isTrue();
    }

    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_2() {
        // given
        int step = 1;

        // when - then
        assertThat(NINE.canMove(step)).isFalse();
    }

    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_3() {
        // given
        int step = -1;

        // when - then
        assertThat(ZERO.canMove(step)).isFalse();
    }
}
