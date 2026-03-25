package domain.strategy;

import domain.Board;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 상_정상_이동() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(0, 1);
        Position to = Position.of(3, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
