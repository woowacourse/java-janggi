package domain.strategy;

import domain.*;
import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class GeneralMoveStrategyTest {

    MoveStrategy strategy;
    Map<Position, Piece> boardMapper;

    @BeforeEach
    void setUp() {
        strategy = new GeneralMoveStrategy();
        boardMapper = new HashMap<>();
        boardMapper.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL, strategy));
    }

    @Test
    @DisplayName("궁의 목적지에 기물이 없으면 이동한다.")
    void generalShouldMoveWhenDestinationIsEmpty() {
        // given
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }

    @Test
    @DisplayName("궁의 목적지에 같은 팀 기물이 있으면 이동하지 않는다.")
    void generalShouldNotMoveWhenDestinationHasAlly() {
        // given
        boardMapper.put(Position.of(1, 5), Piece.of(Team.CHU, Type.SOLDIER, new FixedMoveStrategy()));
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
        boardMapper.put(Position.of(1, 5), Piece.of(Team.HAN, Type.SOLDIER, new FixedMoveStrategy()));
        Board board = Board.of(boardMapper);

        // when
        Position from = Position.of(1, 4);
        Position to = Position.of(1, 5);

        // then
        Assertions.assertTrue(strategy.canMove(from, to, board));
    }
}
