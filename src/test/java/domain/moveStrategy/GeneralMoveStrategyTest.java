package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.General;
import domain.place.piece.Guard;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralMoveStrategyTest {

    @Test
    @DisplayName("궁은 위로 한 칸 이동 가능")
    void should_move_up_successfully() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(6, 5);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 아래로 한 칸 이동 가능")
    void should_move_down_successfully() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(4, 5);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 좌측으로 한 칸 이동 가능")
    void should_move_left_successfully() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(5, 4);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 우측으로 한 칸 이동 가능")
    void should_move_right_successfully() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(5, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 두 칸 이동 불가")
    void cannot_move_more_than_one_step() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(7, 5);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 아군 위치로 이동 불가")
    void cannot_move_to_position_occupied_by_same_team() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        stub.put(new Position(5, 6), new Guard(Side.CHO, new GuardMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(5, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 적군을 잡을 수 있다")
    void should_capture_opponent_piece() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(5, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        stub.put(new Position(5, 6), new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN)));
        Board board = stub.create();

        Position from = new Position(5, 5);
        Position to = new Position(5, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }
}
