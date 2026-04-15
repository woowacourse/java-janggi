package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.piece.Chariot;
import domain.place.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {
    @Test
    @DisplayName("차는 우로 이동 가능하다.")
    void 차_정상_우_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(3, 7);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 좌로 이동 가능하다.")
    void 차_정상_좌_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(3, 2);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 위로 이동 가능하다.")
    void 차_정상_위_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(5, 5);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 아래로 이동 가능하다.")
    void 차_정상_아래_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(1, 5);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 상대편을 먹을 수 있다.")
    void 차_정상_상대편_잡기_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        stubBoard.put(new Position(3, 7), new Chariot(Side.HAN, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(3, 7);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차가 장애물을 중간에 만나면 이동 불가능하다.")
    void 차_장애물_이동_불가() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(3, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        stubBoard.put(new Position(3, 6), new Chariot(Side.HAN, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(3, 5);
        Position to = new Position(3, 7);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차는 궁성 내에서는 대각선 이동이 가능하다")
    void 차_궁성_내_대각선_이동가능() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(8, 4), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(8, 4);
        Position to = new Position(10, 6);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 궁성 내 대각선 이동 시 장애물 있으면 이동 불가능하다")
    void 차_궁성_내_장애물있을때_댁가선_이동불가능() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(8, 6), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        stubBoard.put(new Position(9, 5), new Chariot(Side.CHO, new ChariotMoveStrategy()));
        Board board = stubBoard.create();

        Position from = new Position(8, 6);
        Position to = new Position(10, 4);

        // when
        MoveStrategy moveStrategy = new ChariotMoveStrategy();
        boolean result = moveStrategy.canMove(board, from, to);

        //then
        assertThat(result).isFalse();
    }
}
