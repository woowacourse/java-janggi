package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;
import domain.place.piece.Elephant;
import domain.place.piece.Horse;
import domain.place.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseMoveStrategyTest {

    @Test
    @DisplayName("마는 오른쪽으로 한 칸 이동 후 위쪽 대각선으로 이동할 수 있다.")
    void 마_정상_우측_위대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 오른쪽으로 한 칸 이동 후 아래쪽 대각선으로 이동할 수 있다.")
    void 마_정상_우측_아래대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(4, 7);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 왼쪽으로 한 칸 이동 후 위쪽 대각선으로 이동할 수 있다.")
    void 마_정상_좌측_위대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(6, 3);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 왼쪽으로 한 칸 이동 후 아래쪽 대각선으로 이동할 수 있다.")
    void 마_정상_좌측_아래대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(4, 3);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 위로 한 칸 이동 후 오른쪽 대각선으로 이동할 수 있다.")
    void 마_정상_위_우대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(7, 6);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 위로 한 칸 이동 후 왼쪽 대각선으로 이동할 수 있다.")
    void 마_정상_위_좌대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(7, 4);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 아래로 한 칸 이동 후 오른쪽 대각선으로 이동할 수 있다.")
    void 마_정상_아래_우대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(3, 6);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 아래로 한 칸 이동 후 왼쪽 대각선으로 이동할 수 있다.")
    void 마_정상_아래_좌대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(3, 4);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 도착지에 상대 팀 기물이 있으면 잡을 수 있다.")
    void 마_상대팀_잡기_가능() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        stubBoard.put(new Position(6, 7), new Elephant(Side.HAN, new ElephantMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 이동 경로(첫 칸)에 장애물이 있으면 이동할 수 없다.")
    void 마_경로_장애물_이동_불가() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Horse(Side.CHO, new HorseMoveStrategy()));
        stubBoard.put(new Position(5, 6), new Elephant(Side.HAN, new ElephantMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        // when
        boolean result = new HorseMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }
}
