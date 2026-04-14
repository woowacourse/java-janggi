package domain.strategy;

import domain.*;
import domain.board.*;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class GuardMoveStrategyTest {

    @Test
    @DisplayName("사의 목적지에 기물이 없으면 이동한다.")
    void guardShouldMoveToEmptyDestination() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(1, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사는 궁성 내부에서 대각선 포인트라면 한 칸 이동할 수 있다")
    void guardShouldMoveWhenMovesDiagonallyInPalace() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GUARD, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 4);
        Position targetPosition = Position.of(0, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사는 궁성 내부에서 두 칸 이상 대각선 이동할 수 없다")
    void guardShouldNotMoveWhenMoreThanOneStepDiagonally() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GUARD, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(2, 5);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사는 대각선 포인트가 아닌 곳으로 대각선 이동할 수 없다")
    void guardShouldNotMoveWhenDiagonallyToNonDiagonalPoint() {
    // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 3), Piece.of(Team.CHU, Type.GUARD, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 3);
        Position targetPosition = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("사의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void guardShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new GuardMoveStrategy();
        Board board = BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_HORSE, Formation.LEFT_ELEPHANT_RIGHT_HORSE);

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
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GUARD, strategy));
        boardMapper.put(Position.of(0, 4), Piece.of(Team.HAN, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(0, 4);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }
}
