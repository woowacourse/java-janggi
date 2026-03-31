package domain.strategy;

import domain.Board;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차의 직선 이동 경로에 기물이 없으면 직선 이동한다.")
    void chariotShouldMoveWhenPathIsClear() {
        // given
        MoveStrategy strategy = new ChariotMoveStrategy();

        Map<Position, Piece> boardFactory = new HashMap<>();
        boardFactory.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        boardFactory.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(boardFactory);

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("차의 직선 이동 경로에 기물이 있으면 직선 이동하지 않는다.")
    void chariotShoulNotdMoveWhenPieceInPath() {
        // given
        MoveStrategy strategy = new ChariotMoveStrategy();

        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        boardMapper.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(2, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("차의 이동 목적지에 같은 팀 기물이 있으면 직선 이동하지 않는다.")
    void chariotShouldNotMoveToAllyPiece() {
        // given
        MoveStrategy strategy = new ChariotMoveStrategy();

        Map<Position, Piece> boardFactory = new HashMap<>();
        boardFactory.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        boardFactory.put(Position.of(3, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(boardFactory);

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(3, 0);

        // then
        Assertions.assertFalse(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("차의 이동 목적지에 다른 팀 기물이 있으면 직선 이동한다.")
    void chariotShouldMoveToEnemyPiece() {
        // given
        MoveStrategy strategy = new ChariotMoveStrategy();

        Map<Position, Piece> boardFactory = new HashMap<>();
        boardFactory.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        boardFactory.put(Position.of(0, 0), Piece.of(Team.CHU, Type.CHARIOT, new ChariotMoveStrategy()));
        Board board = Board.of(boardFactory);

        // when
        Position from = Position.of(3, 0);
        Position to = Position.of(6, 0);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
