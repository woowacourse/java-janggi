package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import org.junit.jupiter.api.Test;

class GuardTest {

    @Test
    void 가드는_움직일_수_없다() {
        Guard guard = new Guard(Side.CHO, 1, 2);

        assertThat(guard.isMoveablePosition(null)).isFalse();
    }

    @Test
    void 가드는_움직일_수_있는_경로가_없다() {
        Guard guard = new Guard(Side.CHO, 1, 2);

        assertThat(guard.isMoveablePath(null, null)).isFalse();
    }
}
