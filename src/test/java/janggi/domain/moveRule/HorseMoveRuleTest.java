package janggi.domain.moveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HorseMoveRuleTest {
    private Board board = Board.empty();
    private Position from;
    private Position to;
    private final MoveRule moveRule = new HorseMoveRule();

    @Test
    void 마가_직선이동_후_대각선으로_한칸_이동할수있다() {
        from = new Position(0, 0);
        to = new Position(2, 1);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 마가_이동가능한_패턴이아니면_에러가_발생한다() {
        from = new Position(0, 0);
        to = new Position(3, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 마의_이동경로에_기물이_있으면_에러가발생한다() {
        Board board = Board.of(Map.of(
                new Position(1, 0), new Soldier(Team.HAN)
        ));

        from = new Position(0, 0);
        to = new Position(2, 1);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }
}
