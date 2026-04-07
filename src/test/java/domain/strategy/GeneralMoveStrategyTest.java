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

class GeneralMoveStrategyTest {

    @Test
    @DisplayName("궁의 목적지에 기물이 없으면 이동한다.")
    void generalShouldMoveWhenDestinationIsEmpty() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("궁은 궁성 내부에서 대각선 포인트라면 한 칸 이동할 수 있다")
    void generalShouldMoveWhenMovesDiagonallyInPalace() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 4);
        Position targetPosition = Position.of(0, 3);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("궁은 궁성 내부에서 두 칸 이상 대각선 이동할 수 없다")
    void generalShouldNotMoveWhenMoreThanOneStepDiagonally() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 3), Piece.of(Team.CHU, Type.GENERAL, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(2, 5);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("궁은 대각선 포인트가 아닌 곳으로 대각선 이동할 수 없다")
    void generalShouldNotMoveWhenDiagonallyToNonDiagonalPoint() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 3), Piece.of(Team.CHU, Type.GENERAL, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 3);
        Position targetPosition = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("궁의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void generalShouldNotMoveWhenDestinationHasAlly() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, strategy));
        boardMapper.put(Position.of(1, 5), Piece.of(Team.CHU, Type.GENERAL, new FixedMoveStrategy()));
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("궁의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void generalShouldMoveWhenDestinationHasEnemy() {
        // given
        MoveStrategy strategy = new GeneralMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, strategy));
        boardMapper.put(Position.of(1, 5), Piece.of(Team.HAN, Type.GENERAL, new FixedMoveStrategy()));
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
