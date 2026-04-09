package janggi.domain.moveRule;

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
    void 진영상관없이_좌우로_이동할수있다() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        assertThat(rule.canMove(new Position(3, 4), new Position(3, 5), board)).isTrue();
        assertThat(rule.canMove(new Position(3, 4), new Position(3, 3), board)).isTrue();

        rule = new SoldierMoveRule(Team.HAN);
        assertThat(rule.canMove(new Position(6, 4), new Position(6, 5), board)).isTrue();
        assertThat(rule.canMove(new Position(6, 4), new Position(6, 3), board)).isTrue();

    }

    @Test
    void 초나라_졸은_한_궁성에서_전진_대각선_이동_가능() {
        // 초 전진 = 행 감소
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        assertThat(rule.canMove(
                new Position(2, 5), new Position(1, 4), board)).isTrue();
    }

    @Test
    void 한나라_졸은_초_궁성에서_전진_대각선_이동_가능() {
        // 한 전진 = 행 증가
        MoveRule rule = new SoldierMoveRule(Team.HAN);
        assertThat(rule.canMove(
                new Position(7, 3), new Position(8, 4), board)).isTrue();
    }

    @Test
    void 궁성_중앙에서_꼭짓점으로_전진_대각선_가능() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        // 행 감소 = 전진
        assertThat(rule.canMove(
                new Position(1, 4), new Position(0, 3), board)).isTrue();
    }

    // PR
    @Test
    void 초나라_졸은_한_궁성에서_후진_대각선_이동_불가() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        // 행 증가 대각선 = 후진
        assertThat(rule.canMove(
                new Position(0, 3), new Position(1, 4), board)).isFalse();
    }


    @Test
    void 한나라_졸은_초_궁성에서_후진_대각선_이동_불가() {
        MoveRule rule = new SoldierMoveRule(Team.HAN);
        // 행 감소 대각선 = 후진
        assertThat(rule.canMove(
                new Position(9, 5), new Position(8, 4), board)).isFalse();
    }

    @Test
    void 궁성_밖에서는_대각선_이동_불가() {
        MoveRule rule = new SoldierMoveRule(Team.CHO);
        // 궁성 밖 대각선
        assertThat(rule.canMove(
                new Position(5, 4), new Position(4, 5), board)).isFalse();
    }

    @Test
    void 궁성_대각선_선이_아닌_곳에서는_대각선_불가() {
        MoveRule rule = new SoldierMoveRule(Team.HAN);
        // 궁성 내부지만 중앙 미경유 대각선
        assertThat(rule.canMove(
                new Position(7, 4), new Position(8, 5), board)).isFalse();
    }

}
