package janggi.domain.moveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ElephantMoveRuleTest {

    private Board board = Board.empty();
    private Position from;
    private Position to;
    private final MoveRule moveRule = new ElephantMoveRule();

    @Test
    void 직선_1칸_후_대각_2칸_이동할_수_있다() {
        from = new Position(0, 0);
        to = new Position(2, 3);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }


    @Test
    void 첫_막힘칸에_기물이_있으면_이동할수없다() {
        Board board = Board.of(Map.of(
                new Position(1, 0), new Soldier(Team.HAN)
        ));
        from = new Position(0, 0);
        to = new Position(1, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 두_막힘칸에_기물이_있으면_이동할수없다() {
        Board board = Board.of(Map.of(
                new Position(2, 1), new Soldier(Team.HAN)
        ));

        from = new Position(0, 0);
        to = new Position(1, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

}
