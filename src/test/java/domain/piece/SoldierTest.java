package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import fixture.MoveContextFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {
            "3,7",
            "1,8",
            "2,6"
    })
    void 초_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,6",
            "2,7"
    })
    void 초_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,3",
            "2,5",
            "1,6"
    })
    void 한_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,5",
            "2,4"
    })
    void 한_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,2"
    })
    void 적군_궁성에서는_대각선_전진이_가능하다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(4, 3);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,4"
    })
    void 적군_궁성에서도_뒤쪽_대각선으로는_이동할_수_없다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(4, 3);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(MoveContextFactory.create(from, to)))
                .isInstanceOf(PieceException.class);
    }
}
