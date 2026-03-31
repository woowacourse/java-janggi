package domain.strategy;

import domain.Board;
import domain.BoardFactory;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사의 목적지에 기물이 없으면 이동한다.")
    void guardShouldMoveToEmptyDestination() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void guardShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 2);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }


    @Test
    @DisplayName("사의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void guardShouldMoveToEnemyPiece() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp();

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(6, 0);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }
}
