package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.piece.Elephant;
import domain.place.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("상은 오른쪽으로 한 칸 이동 후 위쪽 대각선으로 이동할 수 있다.")
    void 상_정상_우측_위대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 오른쪽으로 한 칸 이동 후 아래쪽 대각선으로 이동할 수 있다.")
    void 상_정상_우측_아래대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(3, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 왼쪽으로 한 칸 이동 후 위쪽 대각선으로 이동할 수 있다.")
    void 상_정상_좌측_위대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 2);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 왼쪽으로 한 칸 이동 후 아래쪽 대각선으로 이동할 수 있다.")
    void 상_정상_좌측_아래대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(3, 2);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 위로 한 칸 이동 후 오른쪽 대각선으로 이동할 수 있다.")
    void 상_정상_위_우대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(8, 7);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 위로 한 칸 이동 후 왼쪽 대각선으로 이동할 수 있다.")
    void 상_정상_위_좌대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(8, 3);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 아래로 한 칸 이동 후 오른쪽 대각선으로 이동할 수 있다.")
    void 상_정상_아래_우대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(2, 7);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 아래로 한 칸 이동 후 왼쪽 대각선으로 이동할 수 있다.")
    void 상_정상_아래_좌대각_이동() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(2, 3);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 이동 경로에 장애물이 없고 도착지에 상대 팀 기물이 있으면 잡을 수 있다.")
    void 상_상대팀_잡기_가능() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        stubBoard.put(new Position(7, 8), new Elephant(Side.HAN, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상은 도착지에 같은 팀이 있으면 이동할 수 없다.")
    void 상_같은팀_도착지_이동_불가() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        stubBoard.put(new Position(7, 8), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상은 첫 번째 경로에 장애물이 있으면 이동할 수 없다.")
    void 상_첫번째_경로_차단() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        stubBoard.put(new Position(5, 6), new Elephant(Side.HAN, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상은 두 번째 경로에 장애물이 있으면 이동할 수 없다.")
    void 상_두번째_경로_차단() {
        // given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(5, 5), new Elephant(Side.CHO, new ElephantMoveStrategy()));
        stubBoard.put(new Position(6, 7), new Elephant(Side.HAN, new ElephantMoveStrategy()));
        Board board = stubBoard.create();
        Position from = new Position(5, 5);
        Position to = new Position(7, 8);
        MoveStrategy moveStrategy = new ElephantMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }
}
