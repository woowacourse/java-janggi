package domain.strategy;

import domain.Board;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사의 목적지에 기물이 없으면 이동한다.")
    void 사_정상_이동() {
        // given
        GuardMoveStrategy strategy = new GuardMoveStrategy();
        Board board = Board.of();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void 사_목적지에_같은_팀_기물이_있으면_이동_불가() {
        // given
        GuardMoveStrategy strategy = new GuardMoveStrategy();
        Board board = Board.of();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 2);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }


    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void 사_목적지에_다른_팀_기물이_있으면_정상_이동() {
        // given
        GuardMoveStrategy strategy = new GuardMoveStrategy();
        Board board = Board.of();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(6, 0);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }
}
