package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.moveStrategy.CannonMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.Cannon;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonMoveStrategyTest {

    @Test
    @DisplayName("포는 기물을 넘을 수 있다.")
    void should_move_over_piece_when_cannon_has_one_screen_piece() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 기물을 넘어 상대 기물을 먹을 수 있다.")
    void move_over_piece_and_capture_opponent_piece() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        stubBoard.put(new Position(1, 9),
                new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN)));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 9);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannot_move_over_another_cannon() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5), new Cannon(Side.CHO, new CannonMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 기물이 없을 때 넘을 수 없다")
    void cannot_move_when_no_piece_to_jump_over() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 기물이 여러 개일 때 넘을 수 없다")
    void cannot_move_when_multiple_pieces_exist_between() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        stubBoard.put(new Position(1, 6),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 자신의 팀 위치로 이동 불가하다")
    void cannot_move_to_position_occupied_by_same_team() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 4),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        stubBoard.put(new Position(1, 7),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 자신의 위치로 이동 불가하다")
    void cannot_move_to_same_position() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(1, 1);
        Position to = new Position(1, 1);

        // when
        MoveStrategy moveStrategy = new CannonMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }
}
