package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testDouble.TestBoard;
import testDouble.TestBoardFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ElephantTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2,9",
            "3,9",
            "4,9",
            "3,8",
            "4,8",
            "4,7"
    })
    void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> elephant.validateMovable(from, to, board.getBoard()))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,4",
            "7,4",
            "2,9",
            "6,9"
    })
    void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination newFrom = Coordination.of(4, 6);
        board.move(from, newFrom);

        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> elephant.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "7,6",
            "6,1"
    })
    void 움직이는_위치_사이에_기물이_있다면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Elephant elephant = new Elephant(Team.HAN);
        Coordination from = Coordination.of(3, 1);
        Coordination newFrom = Coordination.of(4, 4);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        AssertionsForClassTypes.assertThatThrownBy(() -> elephant.validateMovable(newFrom, to, board.getBoard()))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7,3",
            "7,7"
    })
    void 움직이는_위치_사이에_기물이_없다면_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Elephant elephant = new Elephant(Team.HAN);
        Coordination from = Coordination.of(3, 1);
        Coordination newFrom = Coordination.of(4, 5);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> elephant.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,4",
            "7,4"
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Elephant elephant = new Elephant(Team.HAN);
        Coordination from = Coordination.of(3, 1);
        Coordination newFrom = Coordination.of(5, 7);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        AssertionsForClassTypes.assertThatThrownBy(() -> elephant.validateMovable(newFrom, to, board.getBoard()))
                .isInstanceOf(PieceException.class);
    }
}
