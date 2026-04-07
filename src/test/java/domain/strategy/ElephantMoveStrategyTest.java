package domain.strategy;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("상의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void elephantShouldMoveWhenPathIsClear() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(0, 1);
        Position to = Position.of(3, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("상의 이동 경로에 기물이 있으면 이동하지 않는다.")
    void elephantShouldNotMoveWhenPieceInPath() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(0, 1);
        Position to = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("상의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void elephantShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(6, 1);
        Position to = Position.of(3, 4);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("상의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void elephantShouldMoveToEnemyPiece() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(3, 4);
        Position to = Position.of(6, 2);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
