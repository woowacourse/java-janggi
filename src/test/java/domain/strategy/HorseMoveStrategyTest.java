package domain.strategy;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HorseMoveStrategyTest {

    @Test
    @DisplayName("마의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void horseShouldMoveWhenPathIsClearForChuTeam() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(0, 2);
        Position to = Position.of(2, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("마의 이동 경로에 다른 기물이 없으면 정상 이동한다.")
    void horseShouldMoveWhenPathIsClearForHanTeam() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(9, 2);
        Position to = Position.of(7, 3);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("마의 이동 경로에 기물이 있으면 이동하지 않는다.")
    void horseShouldNotMoveWhenPieceInPath() {
        // given
        MoveStrategy strategy = new ElephantMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(0, 2);
        Position to = Position.of(1, 4);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("마의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void horseShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new HorseMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position from = Position.of(0, 2);
        Position to = Position.of(2, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }
}
