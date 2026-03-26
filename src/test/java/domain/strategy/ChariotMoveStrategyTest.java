package domain.strategy;

import domain.Board;
import domain.BoardFactory;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차의 직선 이동 경로에 기물이 없으면 직선 이동한다.")
    void 차_직선_이동() {
        // given
        ChariotMoveStrategy strategy = new ChariotMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("이동 목적지에 같은 팀 기물이 있으면 직선 이동하지 않는다.")
    void 차_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        ChariotMoveStrategy strategy = new ChariotMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(3, 0);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("이동 목적지에 다른 팀 기물이 있으면 직선 이동한다.")
    void 차_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        ChariotMoveStrategy strategy = new ChariotMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position from = Position.of(3, 0);
        Position to = Position.of(6, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
