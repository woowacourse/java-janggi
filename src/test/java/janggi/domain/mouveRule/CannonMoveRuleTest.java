package janggi.domain.mouveRule;

import janggi.domain.FakeBoard;
import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CannonMoveRuleTest {
    private final MoveRule moveRule = new CannonMoveRule();
    private FakeBoard board;

    @Test
    void 하나의_기물을_넘는_정상_이동_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO));

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 하나의_기물을_넘어_상대_기물_잡기_테스트() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                new Position(0, 4), new Advisor(Team.HAN));

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 넘을_기물이_없으면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = new FakeBoard();

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }


    @Test
    void 경로에_기물이_2개_이상이면_이동_불가하다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 5);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                new Position(0, 4), new Advisor(Team.HAN));

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_다리로_사용할_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Cannon(Team.CHO));

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 포는_잡을_수_없다() {
        // given
        Position from = new Position(0, 0);
        Position to = new Position(0, 4);

        board = FakeBoard.createBoardWith(new Position(0, 2), new Soldier(Team.CHO),
                new Position(0, 4), new Cannon(Team.HAN));

        // when, then
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }
}
