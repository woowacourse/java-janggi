package janggi.domain.moveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class KingMoveRuleTest {
    private final Board board = Board.empty();
    private final MoveRule moveRule = new KingMoveRule();

    //한나라
    @Test
    void 한_궁성_중앙은_상하좌우_모두_이동_가능() {
        Position center = new Position(1, 4);

        assertThat(moveRule.canMove(center, new Position(0, 4), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(2, 4), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(1, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(1, 5), board)).isTrue();
    }

    @Test
    void 한_궁성_위_변은_위로는_이동_불가() {
        Position pos = new Position(0, 4);

        assertThat(moveRule.canMove(pos, new Position(1, 4), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(0, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(0, 5), board)).isTrue();
    }

    @Test
    void 한_궁성_왼쪽_변은_왼쪽으로는_이동_불가() {
        Position pos = new Position(1, 3);

        assertThat(moveRule.canMove(pos, new Position(1, 2), board)).isFalse();
        assertThat(moveRule.canMove(pos, new Position(0, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(2, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(1, 4), board)).isTrue();
    }

    @Test
    void 한_궁성_꼭짓점은_두_방향만_이동_가능() {
        Position pos = new Position(0, 3);

        assertThat(moveRule.canMove(pos, new Position(0, 2), board)).isFalse();
        assertThat(moveRule.canMove(pos, new Position(1, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(0, 4), board)).isTrue();
    }


    //초나라
    @Test
    void 초_궁성_중앙은_상하좌우_모두_이동_가능() {
        Position center = new Position(8, 4);

        assertThat(moveRule.canMove(center, new Position(7, 4), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(9, 4), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(8, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(8, 5), board)).isTrue();
    }

    @Test
    void 초_궁성_아래_변은_아래로는_이동_불가() {
        Position pos = new Position(9, 4);

        assertThat(moveRule.canMove(pos, new Position(8, 4), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(9, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(9, 5), board)).isTrue();
    }

    @Test
    void 초_궁성_왼쪽_변은_왼쪽으로는_이동_불가() {
        Position pos = new Position(8, 3);

        assertThat(moveRule.canMove(pos, new Position(8, 2), board)).isFalse();
        assertThat(moveRule.canMove(pos, new Position(7, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(9, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(8, 4), board)).isTrue();
    }

    @Test
    void 초_궁성_꼭짓점은_두_방향만_이동_가능() {
        Position pos = new Position(9, 3);

        assertThat(moveRule.canMove(pos, new Position(9, 2), board)).isFalse();
        assertThat(moveRule.canMove(pos, new Position(8, 3), board)).isTrue();
        assertThat(moveRule.canMove(pos, new Position(9, 4), board)).isTrue();
    }

    // === 궁성 내 대각선 이동 ===
    @Test
    void 한_궁성_중앙에서_각_꼭짓점으로_대각선_이동_가능() {
        Position center = new Position(1, 4);

        assertThat(moveRule.canMove(center, new Position(0, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(0, 5), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(2, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(2, 5), board)).isTrue();
    }

    @Test
    void 초_궁성_중앙에서_각_꼭짓점으로_대각선_이동_가능() {
        Position center = new Position(8, 4);

        assertThat(moveRule.canMove(center, new Position(7, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(7, 5), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(9, 3), board)).isTrue();
        assertThat(moveRule.canMove(center, new Position(9, 5), board)).isTrue();
    }

    @Test
    void 한_궁성_꼭짓점에서_중앙으로_대각선_이동_가능() {
        assertThat(moveRule.canMove(
                new Position(0, 3), new Position(1, 4), board)).isTrue();
        assertThat(moveRule.canMove(
                new Position(2, 5), new Position(1, 4), board)).isTrue();
    }

    @Test
    void 초_궁성_꼭짓점에서_중앙으로_대각선_이동_가능() {
        assertThat(moveRule.canMove(
                new Position(7, 3), new Position(8, 4), board)).isTrue();
        assertThat(moveRule.canMove(
                new Position(9, 5), new Position(8, 4), board)).isTrue();
    }

    // === 궁성 밖으로 나가는 이동 불가 ===

    @Test
    void 한_궁성에서_궁성_밖으로_나가는_이동은_불가하다() {
        assertThat(moveRule.canMove(
                new Position(0, 3), new Position(0, 2), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(2, 4), new Position(3, 4), board)).isFalse();
    }

    @Test
    void 초_궁성에서_궁성_밖으로_나가는_이동은_불가하다() {
        assertThat(moveRule.canMove(
                new Position(9, 3), new Position(9, 2), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(7, 4), new Position(6, 4), board)).isFalse();
    }

    // === 대각선 선이 아닌 곳에서 대각선 이동 불가 ===

    @Test
    void 한_궁성_내부라도_대각선_선이_아닌_이동은_불가하다() {
        assertThat(moveRule.canMove(
                new Position(0, 4), new Position(1, 5), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(1, 3), new Position(2, 4), board)).isFalse();
    }

    @Test
    void 초_궁성_내부라도_대각선_선이_아닌_이동은_불가하다() {
        assertThat(moveRule.canMove(
                new Position(7, 4), new Position(8, 5), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(8, 3), new Position(9, 4), board)).isFalse();
    }

    // === 2칸 이상 이동 불가 ===
    @Test
    void 한_궁성에서는_대각선_두_칸_이상_이동할_수_없다() {
        assertThat(moveRule.canMove(
                new Position(0, 3), new Position(2, 5), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(0, 5), new Position(2, 3), board)).isFalse();
    }

    @Test
    void 초_궁성에서는_대각선_두_칸_이상_이동할_수_없다() {
        assertThat(moveRule.canMove(
                new Position(7, 3), new Position(9, 5), board)).isFalse();

        assertThat(moveRule.canMove(
                new Position(7, 5), new Position(9, 3), board)).isFalse();
    }
}

