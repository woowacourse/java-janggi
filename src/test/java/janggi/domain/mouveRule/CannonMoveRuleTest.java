package janggi.domain.mouveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;


class CannonMoveRuleTest {

    private Board board = Board.empty();
    private final MoveRule moveRule = new CannonMoveRule();

    @Test
    void 정확히_하나의_기물을_넘어이동할수_있다() {
        Board board = Board.of(Map.of(
                new Position(0, 1), new Soldier(Team.HAN)
        ));

        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isTrue();
    }

    @Test
    void 넘을기물이_없으면_이동할수없다() {
        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 5), board)).isFalse();
    }

    @Test
    void 경로에_기물이_2개_이상이면_이동할수없다() {
        Board board = Board.of(Map.of(
                new Position(0, 2), new Soldier(Team.HAN),
                new Position(0, 3), new Soldier(Team.HAN)
        ));

        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isFalse();
    }

    @Test
    void 포를_포다리로_사용할수없다() {
        Board board = Board.of(Map.of(
                new Position(5, 2), new Cannon(Team.HAN)
        ));
        assertThat(moveRule.canMove(new Position(5, 0), new Position(5, 6), board)).isFalse();
    }

    @Test
    void 포를잡을_수_없다() {
        Board board = Board.of(Map.of(
                new Position(0, 2), new Soldier(Team.HAN),
                new Position(0, 6), new Cannon(Team.HAN)
        ));

        assertThat(moveRule.canMove(new Position(0, 0), new Position(0, 6), board)).isFalse();
    }
}
