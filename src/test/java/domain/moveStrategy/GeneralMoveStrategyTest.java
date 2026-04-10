package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.HanSoldierMoveStrategy;
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
    void 궁_위로_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(10, 5);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 아래로 한 칸 이동 가능")
    void 궁_아래로_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(8, 5);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 좌측으로 한 칸 이동 가능")
    void 궁_좌측_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(9, 4);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 우측으로 한 칸 이동 가능")
    void 궁_우측_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(9, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 두 칸 이동 불가")
    void 궁_두칸_이동_불가() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 4), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 4);
        Position to = new Position(9, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 적군을 잡을 수 있다")
    void 궁_적군_공격_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        stub.put(new Position(9, 6), new Soldier(Side.HAN, new HanSoldierMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(9, 6);

        // when
        boolean result = new GeneralMoveStrategy().canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 궁성 밖으로 이동할 수 없다")
    void 궁_궁성_밖_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 6), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 6);
        Position to = new Position(9, 7);

        // when
        boolean result = new GeneralMoveStrategy()
                .canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 궁성 내에 이어진 선(대각선)으로 이동이 가능하다.")
    void 궁_궁성내_대각선_이동_가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(9, 5), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(9, 5);
        Position to = new Position(8, 6);

        // when
        boolean result = new GeneralMoveStrategy()
                .canMove(board, from, to);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 궁성 내에 이어진 선(대각선)이라도 두 칸 이동은 불가능하다.")
    void 궁_대각선_두칸이동_불가능() {
        // given
        StubBoard stub = new StubBoard();
        stub.put(new Position(10, 4), new General(Side.CHO, new GeneralMoveStrategy()));
        Board board = stub.create();

        Position from = new Position(10, 4);
        Position to = new Position(8, 6);

        // when
        boolean result = new GeneralMoveStrategy()
                .canMove(board, from, to);

        // then
        assertThat(result).isFalse();
    }
}
