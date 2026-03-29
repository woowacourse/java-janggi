package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("초나라 졸은 앞으로 한 칸 이동 가능")
    void cho_soldier_should_move_forward_successfully() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 1);
        Position to = new Position(1, 1);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 좌측 이동 가능")
    void cho_soldier_should_move_left_successfully() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 2);
        Position to = new Position(2, 1);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 우측 이동 가능")
    void cho_soldier_should_move_right_successfully() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 2);
        Position to = new Position(2, 3);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 뒤로 이동 불가")
    void cho_soldier_cannot_move_backward() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(1, 2);
        Position to = new Position(2, 2);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 대각선 이동 불가")
    void cho_soldier_cannot_move_diagonally() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 2);
        Position to = new Position(3, 3);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 두 칸 이동 불가")
    void cho_soldier_cannot_move_two_steps() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(1, 1);
        Position to = new Position(3, 1);
        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 아군 위치로 이동 불가")
    void cho_soldier_cannot_move_to_position_occupied_by_same_team() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        stub.put(to, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));

        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 적군을 잡을 수 있다")
    void cho_soldier_should_capture_opponent_piece() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        stub.put(from, new Soldier(Side.CHO,
                new SoldierMoveStrategy(Side.CHO)));
        stub.put(to, new Soldier(Side.HAN,
                new SoldierMoveStrategy(Side.HAN)));

        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.CHO);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 앞으로 한 칸 이동 가능")
    void han_soldier_should_move_forward_successfully() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(9, 2);
        Position to = new Position(10, 2);

        stub.put(from, new Soldier(Side.HAN,
                new SoldierMoveStrategy(Side.HAN)));

        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.HAN);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 좌우 이동 가능")
    void han_soldier_should_move_horizontally_successfully() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(9, 5);
        Position to = new Position(9, 6);

        stub.put(from, new Soldier(Side.HAN,
                new SoldierMoveStrategy(Side.HAN)));

        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.HAN);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 뒤로 이동 불가")
    void han_soldier_cannot_move_backward() {
        // given
        StubBoard stub = new StubBoard();
        Position from = new Position(10, 5);
        Position to = new Position(9, 5);

        stub.put(from, new Soldier(Side.HAN,
                new SoldierMoveStrategy(Side.HAN)));

        Board board = stub.create();
        MoveStrategy moveStrategy = new SoldierMoveStrategy(Side.HAN);

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }
}
