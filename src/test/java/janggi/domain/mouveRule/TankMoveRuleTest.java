package janggi.domain.mouveRule;

import static janggi.domain.BoardFixture.put;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class TankMoveRuleTest {
    private Board board = Board.empty();
    private Position from;
    private Position to;
    private MoveRule moveRule = new TankMoveRule();


    @Test
    void 도착지점이_출발지점과_같은_행이면_정상적으로_이동한다() {
        from = new Position(0, 0);
        to = new Position(0, 2);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 도착지점이_출발지점과_같은_열이면_정상적으로_이동한다() {
        from = new Position(0, 0);
        to = new Position(2, 0);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }


    @Test
    void 대각선으로_이동할수_없다() {
        from = new Position(0, 0);
        to = new Position(3, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 중간_경로에_기물이_있으면_예외가_발생한다() {
        from = new Position(0, 0);
        to = new Position(0, 3);

        Position other = new Position(0, 2);
        //board.place(other, new Soldier(Team.HAN));
        put(board, other, new Soldier(Team.HAN));

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }
}
