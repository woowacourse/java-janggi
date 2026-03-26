package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testDouble.TestBoard;
import testDouble.TestBoardFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GuardTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,9",
            "4,8",
    })
    public void 초_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> guard.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,9",
            "5,10",
    })
    public void 초_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> guard.validateMovable(from, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,2",
            "6,1",
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        Coordination from = Coordination.of(4, 1);
        Coordination newFrom = Coordination.of(5, 1);
        board.move(from, newFrom);

        Guard guard = new Guard(Team.HAN);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> guard.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }
}
