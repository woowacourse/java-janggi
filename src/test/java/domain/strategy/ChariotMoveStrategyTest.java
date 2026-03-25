package domain.strategy;

import domain.Board;
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
        Board board = Board.of();

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
