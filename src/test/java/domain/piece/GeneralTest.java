package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GeneralTest {

    @ParameterizedTest
    @CsvSource(value = {
            "5,7",
            "4,8",
            "6,8",
    })
    public void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        General general = new General(Team.CHO);
        Coordination from = Coordination.of(5, 9);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> general.validateRule(from, to))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,8",
            "6,9",
            "4,9",
            "5,10",
    })
    public void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        General general = new General(Team.CHO);
        Coordination from = Coordination.of(5, 9);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> general.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
