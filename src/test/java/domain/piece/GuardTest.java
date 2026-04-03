package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import fixture.BoardFixtureFactory;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,9",
            "4,8",
    })
    void 초_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> guard.validateMovable(from, to, board))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,9",
            "5,10",
    })
    void 초_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> guard.validateMovable(from, to, board))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,2",
            "6,1",
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(4, 1), Coordination.of(5, 1))
                .map();

        Guard guard = new Guard(Team.HAN);
        Coordination from = Coordination.of(5, 1);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> guard.validateMovable(from, to, board))
                .isInstanceOf(PieceException.class);
    }
}
