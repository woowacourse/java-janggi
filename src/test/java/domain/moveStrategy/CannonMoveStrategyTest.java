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
    void 포_정상_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
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
    void 포_기물_넘어_이동() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
        stubBoard.put(new Position(1, 9),
                new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN.getSoldierDirections())));
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
    void 포_동일_기물_넘기() {
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
    void 포_기물_없음() {
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
    void 포_기물_여러개() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 5),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
        stubBoard.put(new Position(1, 6),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
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
    void 포_자신의_팀_위치로_이동불가() {
        //given
        StubBoard stubBoard = new StubBoard();
        stubBoard.put(new Position(1, 1), new Cannon(Side.CHO, new CannonMoveStrategy()));
        stubBoard.put(new Position(1, 4),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
        stubBoard.put(new Position(1, 7),
                new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO.getSoldierDirections())));
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
    void 포_자신의_위치_이동불가() {
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
