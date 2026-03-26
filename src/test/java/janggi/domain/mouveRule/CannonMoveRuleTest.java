package janggi.domain.mouveRule;

import static janggi.domain.BoardFixture.put;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;


class CannonMoveRuleTest {

    private Board board = Board.empty();
    private final MoveRule moveRule = new CannonMoveRule();

    @Test
    void 정확히_하나의_기물을_넘어이동할수_있다() {

        Position other = new Position(0, 1);
        put(board, other, new Soldier(Team.HAN));
        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isTrue();
    }

    @Test
    void 넘을기물이_없으면_이동할수없다() {
        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 5), board)).isFalse();
    }

    @Test
    void 경로에_기물이_2개_이상이면_이동할수없다() {

        Position other1 = new Position(0, 2);
        Position other2 = new Position(0, 3);
        put(board, other1, new Soldier(Team.HAN));
        put(board, other2, new Soldier(Team.HAN));

        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isFalse();
    }

    @Test
    void 포를_포다리로_사용할수없다() {
        Position other = new Position(0, 2);
        put(board, other, new Cannon(Team.HAN));

        assertThat(moveRule.canMove(new Position(5, 0), new Position(5, 6), board)).isFalse();
    }

    @Test
    void 포를잡을_수_없다() {

        Position other1 = new Position(0, 2);
        put(board, other1, new Cannon(Team.HAN));

        Position other2 = new Position(0, 6);
        put(board, other2, new Cannon(Team.HAN));

        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isFalse();
    }


}
