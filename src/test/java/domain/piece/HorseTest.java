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

class HorseTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,8",
            "4,7",
            "5,8",
            "4,9",
    })
    public void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> horse.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,5",
            "2,7",
            "3,4",
            "3,8",
            "5,4",
            "5,8",
            "6,5",
            "6,7",
    })
    public void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination newFrom = Coordination.of(4, 6);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> horse.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2",
            "3,2",
            "4,3",
            "4,5",
    })
    void 움직이는_위치_사이에_기물이_있다면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Horse horse = new Horse(Team.HAN);
        Coordination from = Coordination.of(2, 1);
        Coordination newFrom = Coordination.of(2, 4);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        AssertionsForClassTypes.assertThatThrownBy(() -> horse.validateMovable(newFrom, to, board.getBoard()));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "3,6"
    })
    void 움직이는_위치_사이에_기물이_없다면_예러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Horse horse = new Horse(Team.HAN);
        Coordination from = Coordination.of(2, 1);
        Coordination newFrom = Coordination.of(2, 4);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> horse.validateMovable(newFrom, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,4",
            "7,4"
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Horse horse = new Horse(Team.HAN);
        Coordination from = Coordination.of(2, 1);
        Coordination newFrom = Coordination.of(5, 3);
        board.move(from, newFrom);
        Coordination to = Coordination.of(column, row);

        AssertionsForClassTypes.assertThatThrownBy(() -> horse.validateMovable(newFrom, to, board.getBoard()));
    }
}
