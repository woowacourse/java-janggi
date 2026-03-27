package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testDouble.TestBoard;
import testDouble.TestBoardFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {
            "4,7",
            "2,7",
            "4,6",
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있지_않다면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(2, 8);
        Coordination newFrom = Coordination.of(3, 8);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateMovable(newFrom, to, board.getBoard())).isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,7",
            "6,7",
            "4,3",
            "4,9",
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있다면_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(2, 8);
        Coordination newFrom = Coordination.of(4, 7);
        Coordination generalFrom = Coordination.of(5, 9);
        Coordination generalNewFrom = Coordination.of(4, 8);
        Coordination soldierFrom = Coordination.of(3, 4);
        Coordination soldierNewFrom = Coordination.of(4, 4);
        board.move(from, newFrom);
        board.move(generalFrom, generalNewFrom);
        board.move(soldierFrom, soldierNewFrom);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> cannon.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,4",
            "8,4"
    })
    void 움직이는_위치_사이에_기물이_1개가_아니라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(2, 3);
        Coordination newFrom = Coordination.of(2, 4);
        board.move(from, newFrom);

        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateMovable(newFrom, to, board.getBoard())).isInstanceOf(PieceException.class);
        ;
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,1",
            "2,4",
            "6,4",
            "4,9",
    })
    void 움직이는_위치_사이에_기물이_1개라면_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(2, 3);
        Coordination newFrom = Coordination.of(4, 4);
        Coordination guardFrom = Coordination.of(4, 1);
        Coordination guardNewFrom = Coordination.of(4, 2);
        Coordination generalFrom = Coordination.of(5, 9);
        Coordination generalNewFrom = Coordination.of(4, 8);
        board.move(from, newFrom);
        board.move(guardFrom, guardNewFrom);
        board.move(generalFrom, generalNewFrom);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> cannon.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,9",
            "9,3",
    })
    void 움직이는_위치_사이에_포라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(2, 3);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);

    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,7",
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(2, 8);
        Coordination newFrom = Coordination.of(2, 7);
        Coordination to = Coordination.of(column, row);
        board.move(from, newFrom);

        assertThatThrownBy(() -> cannon.validateMovable(newFrom, to, board.getBoard()))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,3",
            "8,8",
    })
    void 도착지의_기물이_포라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");
        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(2, 8);
        Coordination soldierFrom = Coordination.of(1, 7);
        Coordination soldierNewFrom = Coordination.of(2, 7);
        Coordination generalFrom = Coordination.of(5, 9);
        Coordination generalNewFrom = Coordination.of(4, 8);
        Coordination to = Coordination.of(column, row);
        board.move(soldierFrom, soldierNewFrom);
        board.move(generalFrom, generalNewFrom);

        assertThatThrownBy(() -> cannon.validateMovable(from, to, board.getBoard()))
                .isInstanceOf(PieceException.class);
    }
}
