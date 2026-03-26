package janggi.domain.mouveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class KingMoveRuleTest {

    private Board board = Board.empty();
    private final MoveRule moveRule = new KingMoveRule();

    @Test
    void 상하좌우_1칸_이동가능() {
        assertThat(moveRule.canMove(new Position(4, 4), new Position(5, 4), board)).isTrue();
        assertThat(moveRule.canMove(new Position(4, 4), new Position(3, 4), board)).isTrue();
        assertThat(moveRule.canMove(new Position(4, 4), new Position(4, 5), board)).isTrue();
        assertThat(moveRule.canMove(new Position(4, 4), new Position(4, 3), board)).isTrue();
    }

    @Test
    void _2칸이상_이동못함() {
        assertThat(moveRule.canMove(new Position(4, 4), new Position(6, 4), board)).isFalse();
        assertThat(moveRule.canMove(new Position(4, 4), new Position(4, 6), board)).isFalse();
        assertThat(moveRule.canMove(new Position(4, 4), new Position(6, 6), board)).isFalse();
    }
}
