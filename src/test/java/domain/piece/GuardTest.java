package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GuardTest {

    @ParameterizedTest
    @CsvSource(value = {"3,9", "4,8"})
    public void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> guard.validateRule(from, to))
                .isInstanceOf(PieceException.class)
                .hasMessageContaining(Piece.IMPOSSIBLE_MOVE_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(value = {"4,9", "5,10"})
    public void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Guard guard = new Guard(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> guard.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
