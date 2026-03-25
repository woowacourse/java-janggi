package janggi.domain.mouveRule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class HorseMoveRuleTest {
    private Board board = new Board("테스트");
    private Position from;
    private Position to;
    private final MoveRule moveRule = new HorseMoveRule();

    @Test
    void 마가_직선이동_후_대각선으로_한칸_이동할수있다() {
        from = new Position(0, 0);
        to = new Position(2, 1);

        assertDoesNotThrow(() -> moveRule.move(from, to, board));
    }

    @Test
    void 마가_이동가능한_패턴이아니면_에러가_발생한다() {
        from = new Position(0, 0);
        to = new Position(3, 3);

        assertThatThrownBy(() -> moveRule.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("마는");
    }

    @Test
    void 마의_이동경로에_기물이_있으면_에러가발생한다() {
        from = new Position(0, 0);
        to = new Position(2, 1);

        Position other = new Position(1, 0);
        board.place(other, new Soldier(Team.HAN));

        assertThatThrownBy(() -> moveRule.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동경로에 기물이");
    }


}
