package domain.movement;

import domain.coordination.Coordination;
import domain.piece.Team;
import domain.piece.error.PalaceMovementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FullPalaceMovementTest {

    @Nested
    class HAN_진영의_궁성인_경우 {

        private final FullPalaceMovement fullPalaceMovement = new FullPalaceMovement(Team.HAN);

        @ParameterizedTest
        @CsvSource(value = {
                "4,1,4,2",
                "4,1,5,2",
                "5,2,4,1",
                "5,2,6,3",
                "6,3,5,2",
                "6,3,6,2"
        })
        public void 두_좌표가_모두_같은_궁성_안에_있으면_true를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThat(isPalace(fromColumn, fromRow, toColumn, toRow)).isTrue();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,4,5,2",
                "3,2,5,2",
                "7,1,5,1",
                "5,2,4,4",
                "5,2,3,2",
                "5,1,7,1",
                "4,8,5,9",
                "5,9,6,10",
                "4,10,5,10",
        })
        public void from과_to_중에_하나라도_궁성_밖이면_false를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThat(isPalace(fromColumn, fromRow, toColumn, toRow)).isFalse();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,1,4,2",
                "4,2,5,2",
                "5,2,4,1",
                "5,2,6,3",
                "6,3,5,3",
                "4,3,4,2"
        })
        public void 궁성_내의_이동_규칙을_지킬_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThatCode(() -> validateRule(fromColumn, fromRow, toColumn, toRow)).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,1,6,1",
                "4,1,4,3",
                "4,1,6,3",
                "5,1,5,3",
                "4,3,6,1"
        })
        public void 궁성_내_이동_불가능한_경로이면_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThatThrownBy(() -> validateRule(fromColumn, fromRow, toColumn, toRow))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }

        private boolean isPalace(int fromColumn, int fromRow, int toColumn, int toRow) {
            return fullPalaceMovement.isPalace(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow));
        }

        private void validateRule(int fromColumn, int fromRow, int toColumn, int toRow) {
            fullPalaceMovement.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow));
        }
    }

    @Nested
    class CHO_진영의_궁성인_경우 {

        private final FullPalaceMovement fullPalaceMovement = new FullPalaceMovement(Team.CHO);

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,4,9",
                "4,8,5,9",
                "5,9,4,8",
                "5,9,6,10",
                "6,10,5,9",
                "6,10,6,9"
        })
        public void 두_좌표가_모두_같은_궁성_안에_있으면_true를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThat(isPalace(fromColumn, fromRow, toColumn, toRow)).isTrue();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,7,5,9",
                "3,8,5,9",
                "7,10,5,10",
                "5,9,4,7",
                "5,9,3,8",
                "5,10,7,10",
                "4,1,5,2",
                "5,2,6,3",
                "4,3,5,3",
        })
        public void from과_to_중에_하나라도_궁성_밖이면_false를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThat(isPalace(fromColumn, fromRow, toColumn, toRow)).isFalse();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,4,9",
                "4,9,5,9",
                "5,9,4,8",
                "5,9,6,10",
                "6,10,5,10",
                "4,10,4,9"
        })
        public void 궁성_내의_이동_규칙을_지킬_경우_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThatCode(() -> validateRule(fromColumn, fromRow, toColumn, toRow)).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource(value = {
                "4,8,6,8",
                "4,8,4,10",
                "4,8,6,10",
                "5,8,5,10",
                "4,10,6,8"
        })
        public void 궁성_내_이동_불가능한_경로이면_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
            assertThatThrownBy(() -> validateRule(fromColumn, fromRow, toColumn, toRow))
                    .isExactlyInstanceOf(PalaceMovementException.class);
        }

        private boolean isPalace(int fromColumn, int fromRow, int toColumn, int toRow) {
            return fullPalaceMovement.isPalace(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow));
        }

        private void validateRule(int fromColumn, int fromRow, int toColumn, int toRow) {
            fullPalaceMovement.validateRule(
                    Coordination.of(fromColumn, fromRow),
                    Coordination.of(toColumn, toRow));
        }
    }
}
