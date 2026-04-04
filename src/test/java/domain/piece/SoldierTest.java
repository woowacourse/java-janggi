package domain.piece;

import domain.coordination.Coordination;
import domain.movement.ForwardPalaceMovement;
import domain.piece.error.PieceException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class SoldierTest {

    @ParameterizedTest
    @CsvSource(value = {"3,7", "1,8", "2,6"})
    public void 초_진영_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(from, to))
                .isInstanceOf(PieceException.class)
                .hasMessageContaining(Piece.IMPOSSIBLE_MOVE_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,6", "2,7"})
    public void 초_진영_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.CHO);
        Coordination from = Coordination.of(1, 7);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(from, to))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {"1,3", "2,5", "1,6"})
    public void 한_진영_기물에서_이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> soldier.validateRule(from, to))
                .isInstanceOf(PieceException.class)
                .hasMessageContaining(Piece.IMPOSSIBLE_MOVE_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,5", "2,4"})
    public void 한_진영_기물에서_이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Soldier soldier = new Soldier(Team.HAN);
        Coordination from = Coordination.of(1, 4);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> soldier.validateRule(from, to))
                .doesNotThrowAnyException();
    }

    @Nested
    class 한_진영에서_초_진영의_기물의_궁성_이동 {

        @ParameterizedTest
        @CsvSource(value = {
                "4,3,4,2",
                "4,3,5,2",
                "4,3,5,3",
                "5,2,4,1",
                "5,2,5,1",
                "5,2,6,1",
                "5,2,6,2",
                "5,2,4,2"
        })
        public void 궁성_내_이동_가능한_위치일_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            Soldier soldier = new Soldier(Team.CHO);
            assertThatCode(() -> soldier.validateRule(Coordination.of(fromColumn, fromRow), Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,3,6,3",
                "4,3,4,1",
                "4,2,4,3",
                "4,2,5,1",
                "5,2,5,3"
        })
        public void 궁성_내_이동_불가능한_위치일_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            Soldier soldier = new Soldier(Team.CHO);
            assertThatThrownBy(() -> soldier.validateRule(Coordination.of(fromColumn, fromRow), Coordination.of(toColumn, toRow)))
                    .isInstanceOf(PieceException.class)
                    .hasMessageContaining(ForwardPalaceMovement.IMPOSSIBLE_PALACE_MOVE_MESSAGE);
        }
    }

    @Nested
    class 초_진영에서_한_진영의_기물의_궁성_이동 {

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,4,9",
                "4,8,5,9",
                "5,9,4,10",
                "5,9,5,10",
                "5,9,6,10",
                "5,10,6,10",
                "5,10,4,10"
        })
        public void 궁성_내_이동_가능한_위치일_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            Soldier soldier = new Soldier(Team.HAN);
            assertThatCode(() -> soldier.validateRule(Coordination.of(fromColumn, fromRow), Coordination.of(toColumn, toRow)))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,6,8",
                "4,8,6,10",
                "5,8,4,9",
                "5,9,5,8",
                "4,10,4,9"
        })
        public void 궁성_내_이동_불가능한_위치일_경우_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            Soldier soldier = new Soldier(Team.HAN);
            assertThatThrownBy(() -> soldier.validateRule(Coordination.of(fromColumn, fromRow), Coordination.of(toColumn, toRow)))
                    .isInstanceOf(PieceException.class)
                    .hasMessageContaining(ForwardPalaceMovement.IMPOSSIBLE_PALACE_MOVE_MESSAGE);
        }
    }
}
