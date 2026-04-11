package domain.movement;

import domain.coordination.Coordination;
import domain.piece.error.PalaceMovementException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class DiagonalPalaceMovementTest {

    private final DiagonalPalaceMovement diagonalPalaceMovement = new DiagonalPalaceMovement();

    @ParameterizedTest
    @CsvSource(value = {
            "4,8,5,9",
            "4,8,6,10",
            "5,9,6,8",
            "6,10,4,8",
            "4,1,5,2",
            "4,1,6,3",
            "5,2,6,1",
            "6,3,4,1"
    })
    public void 같은_궁성_안의_대각선_좌표이면_true를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
        assertThat(diagonalPalaceMovement.isPalace(
                Coordination.of(fromColumn, fromRow),
                Coordination.of(toColumn, toRow))).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8,4,1",
            "5,9,5,2",
            "6,10,6,3",
            "4,8,5,8",
            "4,8,4,9",
            "5,9,5,8",
            "1,1,2,2",
            "3,5,4,6"
    })
    public void 같은_궁성_안의_대각선_좌표가_아니면_false를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
        assertThat(diagonalPalaceMovement.isPalace(
                Coordination.of(fromColumn, fromRow),
                Coordination.of(toColumn, toRow))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8,5,9",
            "4,8,6,10",
            "5,9,4,8",
            "5,9,6,8",
            "5,9,6,10",
            "5,9,4,10",
            "4,1,5,2",
            "4,1,6,3",
            "5,2,4,1",
            "5,2,6,3",
            "6,3,4,1",
    })
    public void 궁성_내_이동_가능한_경로이면_에러를_반환하지_않는다(int fromColumn, int fromRow, int toColumn, int toRow) {
        assertThatCode(() -> diagonalPalaceMovement.validateRule(
                Coordination.of(fromColumn, fromRow),
                Coordination.of(toColumn, toRow))).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8,4,10",
            "6,8,4,8",
            "4,1,4,3",
            "4,3,6,3"
    })
    public void 궁성_내_이동_불가능한_경로이면_에러를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow) {
        assertThatThrownBy(() -> diagonalPalaceMovement.validateRule(
                Coordination.of(fromColumn, fromRow),
                Coordination.of(toColumn, toRow)))
                .isExactlyInstanceOf(PalaceMovementException.class);
    }
}
