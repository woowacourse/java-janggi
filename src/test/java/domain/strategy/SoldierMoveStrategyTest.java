package domain.strategy;

import domain.*;
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
