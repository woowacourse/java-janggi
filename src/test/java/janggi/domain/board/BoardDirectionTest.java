package janggi.domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardDirectionTest {

    @Test
    void testUpForwardDirection() {
        // given
        BoardDirection up = BoardDirection.UP;

        // when & then
        Assertions.assertThat(up.isForward(1)).isTrue();
    }

    @Test
    void testDownForwardDirection() {
        // given
        BoardDirection down = BoardDirection.DOWN;

        // when & then
        Assertions.assertThat(down.isForward(-1)).isTrue();
    }
}
