package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testDouble.TestBoard;
import testDouble.TestBoardFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GeneralTest {

    @ParameterizedTest
    @CsvSource(value = {
            "5,7",
            "4,8",
            "6,8",
    })
    public void 초_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        General general = new General(Team.CHO);
        Coordination from = Coordination.of(5, 9);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> general.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,8",
            "6,9",
            "4,9",
            "5,10",
    })
    public void 초_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");

        General general = new General(Team.CHO);
        Coordination from = Coordination.of(5, 9);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> general.validateMovable(from, to, board.getBoard()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,1",
            "6,1",
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        TestBoard board = TestBoardFactory.create("1", "1");
        Coordination from = Coordination.of(5, 2);
        Coordination newFrom = Coordination.of(5, 1);
        board.move(from, newFrom);

        General general = new General(Team.HAN);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> general.validateMovable(from, to, board.getBoard())).isInstanceOf(PieceException.class);
    }
}
