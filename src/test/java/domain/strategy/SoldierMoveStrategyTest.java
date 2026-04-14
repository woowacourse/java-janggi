package domain.strategy;

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

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("졸의 목적지에 기물이 없으면 이동한다.")
    void soldierShouldMoveWhenDestinationIsEmpty() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(3, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("졸/병은 궁성 내부에서 대각선 포인트라면 한 칸 이동할 수 있다")
    void soldierShouldMoveWhenMovesDiagonallyInPalace() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 4);
        Position targetPosition = Position.of(2, 5);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("졸/병은 궁성 내부에서 두 칸 이상 대각선 이동할 수 없다")
    void soldierShouldNotMoveWhenMoreThanOneStepDiagonally() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(0, 3), Piece.of(Team.CHU, Type.SOLDIER, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(0, 3);
        Position targetPosition = Position.of(2, 5);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("졸/병은 대각선 포인트가 아닌 곳으로 대각선 이동할 수 없다")
    void soldierShouldNotMoveWhenDiagonallyToNonDiagonalPoint() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 3), Piece.of(Team.CHU, Type.SOLDIER, strategy));
        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(1, 3);
        Position targetPosition = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("졸의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void soldierShouldNotMoveWhenDestinationHasAlly() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(3, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));
        boardMapper.put(Position.of(4, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }


    @Test
    @DisplayName("졸의 목적지에 다른 팀 기물이 있으면 이동한다.")
    void soldierShouldMoveWhenDestinationHasEnemy() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(3, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));
        boardMapper.put(Position.of(4, 4), Piece.of(Team.HAN, Type.SOLDIER, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(4, 4);

        // then
        Assertions.assertTrue(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("초나라 졸의 이동 경로가 후퇴이면 이동할 수 없다.")
    void soldierShouldNotRetreatForChuTeam() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(3, 4), Piece.of(Team.CHU, Type.SOLDIER, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(3, 4);
        Position targetPosition = Position.of(2, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }

    @Test
    @DisplayName("한나라 졸의 이동 경로가 후퇴이면 이동할 수 없다.")
    void soldierShouldNotRetreatForHanTeam() {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy();
        Map<Position, Piece> boardMapper = new HashMap<>();
        boardMapper.put(Position.of(6, 4), Piece.of(Team.HAN, Type.SOLDIER, strategy));

        Board board = Board.of(boardMapper);

        // when
        Position position = Position.of(6, 4);
        Position targetPosition = Position.of(7, 4);

        // then
        Assertions.assertFalse(strategy.canMove(position, targetPosition, board));
    }
}
