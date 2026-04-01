package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {"4,7", "2,7", "4,6"})
    void 수직_수평_위치가_아니라면_에러를_반환한다(int column, int row) {
        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(3, 8);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateRule(from, to))
                .isInstanceOf(PieceException.class)
                .hasMessageContaining(Piece.IMPOSSIBLE_MOVE_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,8", "6,8", "3,5", "3,10"})
    void 수직_수평_위치라면_에러를_반환하지_않는다(int column, int row) {
        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(3, 8);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> cannon.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
