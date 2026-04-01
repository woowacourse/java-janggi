package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ChariotTest {

    @ParameterizedTest
    @CsvSource(value = {"2,9", "4,9"})
    void 수직_수평_위치가_아니라면_에러를_반환한다(int column, int row) {
        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> chariot.validateRule(from, to))
                .isInstanceOf(PieceException.class)
                .hasMessageContaining(Piece.IMPOSSIBLE_MOVE_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,9", "1,8", "2,10"})
    void 수직_수평_위치라면_에러를_반환하지_않는다(int column, int row) {
        Chariot chariot = new Chariot(Team.CHO);
        Coordination from = Coordination.of(1, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> chariot.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
