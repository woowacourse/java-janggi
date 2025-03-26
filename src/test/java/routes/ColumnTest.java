package routes;

import static janggi.position.Column.A;
import static janggi.position.Column.I;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColumnTest {

    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_1() {
        // given
        int step = 1;

        // when - then
        assertThat(A.canMove(step)).isTrue();
    }

    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_2() {
        // given
        int step = 1;

        // when - then
        assertThat(I.canMove(step)).isFalse();
    }

    @Test
    @DisplayName("스텝만큼 이동이 가능한지 검사할 수 있다.")
    void canMoveTest_3() {
        // given
        int step = -1;

        // when - then
        assertThat(A.canMove(step)).isFalse();
    }
}
