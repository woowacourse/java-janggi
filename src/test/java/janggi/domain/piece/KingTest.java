package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void 킹은_움직일_수_없다() {
        King king = new King(Side.CHO, 1, 2);

        assertThat(king.isMoveablePosition(null)).isFalse();
    }

    @Test
    void 킹은_움직일_수_있는_경로가_없다() {
        King king = new King(Side.CHO, 1, 2);

        assertThat(king.isMoveablePath(null, null)).isFalse();
    }
}
