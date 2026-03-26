package janggi.domain.mouveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class SoldierMoveRuleTest {
    private Board board = Board.empty();

    @Test
    void 초나라에서는_위_행감소_방향으로_전진할수있다() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        assertThat(rule.canMove(new Position(4, 4), new Position(3, 4), board)).isTrue();
    }


    @Test
    void 초나라에서는_아래_행증가_방향으로_후진할수없다() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        assertThat(rule.canMove(new Position(2, 4), new Position(3, 4), board)).isFalse();
    }


    @Test
    void 한나라에서는_아래_행증가_방향으로_전진할수있다() {
        MoveRule rule = new SoldierMoveRule(Team.HAN);
        assertThat(rule.canMove(new Position(4, 4), new Position(5, 4), board)).isTrue();
    }


    @Test
    void 한나라에서는_위_행감소_방향으로_후진할수없다() {
        MoveRule rule = new SoldierMoveRule(Team.HAN);
        assertThat(rule.canMove(new Position(2, 4), new Position(1, 4), board)).isFalse();
    }


    @Test
        //TODO
    void 진영상관없이_좌우로_이동할수있다() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        assertThat(rule.canMove(new Position(3, 4), new Position(3, 5), board)).isTrue();
        assertThat(rule.canMove(new Position(3, 4), new Position(3, 3), board)).isTrue();

        rule = new SoldierMoveRule(Team.HAN);
        assertThat(rule.canMove(new Position(6, 4), new Position(6, 5), board)).isTrue();
        assertThat(rule.canMove(new Position(6, 4), new Position(6, 3), board)).isTrue();

    }


}
