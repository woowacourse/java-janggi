package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class 차Test {

    @ParameterizedTest
    @CsvSource({"5, 1", "0, 9", "1, 1", "0, 2"})
    void 수직_수평으로_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        차 piece = new 차(Side.초);
        int beforeRow = 0;
        int beforeColumn = 1;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @ParameterizedTest
    @CsvSource({"9, 2", "5, 8"})
    void 수직_수평이_아닌_경우_이동할_수_없다(int afterRow, int afterColumn) {
        // given
        차 piece = new 차(Side.초);
        int beforeRow = 0;
        int beforeColumn = 1;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }
}
