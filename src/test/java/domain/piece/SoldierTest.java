package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,7",
            "1,8",
            "2,6"
    })
    public void 초_진영_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(from, to))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "2,7"
    })
    public void 초_진영_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(from, to))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,3",
            "2,5",
            "1,6"
    })
    public void 한_진영_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(from, to))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,5",
            "2,4"
    })
    public void 한_진영_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
