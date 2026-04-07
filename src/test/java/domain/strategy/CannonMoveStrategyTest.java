package domain.strategy;

import domain.*;
import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CannonMoveStrategyTest {

    @Test
    @DisplayName("포의 이동 경로에 포를 제외한 기물이 1개 있으면 정상 이동한다.")
    void cannonShouldMoveWhenOnePieceInPath() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(4, 1);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포는 궁성 내부에서 대각선으로 이동할 수 있다")
    void cannonShouldMoveWhenMovesDiagonallyInPalace() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 3), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(2, 5);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("차는 대각선 포인트가 아닌 곳으로 대각선 이동할 수 없다")
    void cannonShouldNotMoveWhenDiagonallyToNonDiagonalPoint() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 3), Piece.of(Team.CHU, Type.CANNON, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 3);
        Position targetPosition = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("포의 이동 경로에 다른 기물이 없으면 이동하지 못한다.")
    void cannonShouldNotMoveWhenNoPieceInPath() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 이동 경로에 기물이 둘 이상 있으면 이동하지 않는다.")
    void cannonShouldNotMoveWhenMultiplePiecesInPath() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
        boardMapper.put(Position.of(4, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void cannonShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 목적지에 포가 있으면 이동하지 않는다.")
    void cannonShouldNotMoveToAnotherCannon() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.CANNON, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void cannonShouldMoveToEnemyPiece() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("포의 이동 경로에 포가 존재하면 이동하지 못한다.")
    void cannonShouldNotMoveWhenCannonInPath() {
        // given
        MoveStrategy strategy = new CannonMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(2, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(3, 1), Piece.of(Team.CHU, Type.CANNON, strategy));
        boardMapper.put(Position.of(5, 1), Piece.of(Team.HAN, Type.SOLDIER, new FixedMoveStrategy()));

        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(2, 1);
        Position to = Position.of(5, 1);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }
}
