package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.moveStrategy.HanSoldierMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierMoveStrategyTest {

    @Test
    @DisplayName("초나라 졸은 앞으로 한 칸 이동 가능")
    void 초나라_졸_앞으로_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(2, 1), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(2, 1);
        Position to = new Position(1, 1);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 좌측 이동 가능")
    void 초나라_졸_좌측_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(2, 2), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(2, 2);
        Position to = new Position(2, 1);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 우측 이동 가능")
    void 초나라_졸_우측_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(2, 2), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 궁성 내에서 간선 이동 가능")
    void 초나라_졸_궁성내_간선_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(10, 4), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(10, 4);
        Position to = new Position(9, 5);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초나라 졸은 뒤로 이동 불가")
    void 초나라_졸_뒤로_이동_불가() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(1, 2), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(1, 2);
        Position to = new Position(2, 2);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 대각선 이동 불가")
    void 초나라_졸_대각선_이동_불가() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(2, 2), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(2, 2);
        Position to = new Position(3, 3);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 두 칸 이동 불가")
    void 초나라_졸_두칸_이동_불가() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(1, 1), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(1, 1);
        Position to = new Position(3, 1);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("초나라 졸은 적군을 잡을 수 있다")
    void 초나라_졸_적군_공격_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(2, 2), new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        stub.put(new Position(2, 3), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 앞으로 한 칸 이동 가능")
    void 한나라_졸_앞으로_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 2), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 2);
        Position to = new Position(10, 2);

        MoveStrategy moveStrategy = new HanSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 좌우 이동 가능")
    void 한나라_졸_좌우_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(9, 6);

        MoveStrategy moveStrategy = new HanSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 궁성 내에서 간선 이동 가능")
    void 한나라_졸_궁성내_간선_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(3, 4), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(3, 4);
        Position to = new Position(2, 5);

        MoveStrategy moveStrategy = new ChoSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("한나라 졸은 뒤로 이동 불가")
    void 한나라_졸_뒤로_이동_불가() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(10, 5), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(10, 5);
        Position to = new Position(9, 5);

        MoveStrategy moveStrategy = new HanSoldierMoveStrategy();

        // when
        boolean result = moveStrategy.canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }
}
