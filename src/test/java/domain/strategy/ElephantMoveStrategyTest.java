package domain.strategy;

import domain.Board;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("초나라 상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 초나라_상_정상_이동() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(0, 1);
        Position to = Position.of(3, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("한나라 상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void 한나라_상_정상_이동() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(9, 1);
        Position to = Position.of(6, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("초나라 상의 이동 경로에 기물이 있으면 이동하지 않는다.")
    void 초나라_상_이동_경로에_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(0, 1);
        Position to = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 상_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(6, 1);
        Position to = Position.of(3, 4);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("목적지에 다른 팀 기물이 있으면 이동한다.")
    void 상_목적지에_다른_팀_기물이_있으면_이동_가능() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = Board.of();

        // when
        Position from = Position.of(3, 4);
        Position to = Position.of(6, 2);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
