package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class 상Test {
    @ParameterizedTest
    @CsvSource({"4, 8", "8, 8", "9, 7", "9, 3", "8,2", "4,2", "3,3", "3,7"})
    void 상_하_좌_우로_한_칸_이동하고_대각선으로_두_칸_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        상 piece = new 상(Side.초);
        int beforeRow = 6;
        int beforeColumn = 5;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @Test
    void 상_하_좌_우로_한_칸과_대각선으로_두_칸_이동할_수_없다() {
        // given
        상 piece = new 상(Side.초);
        int beforeRow = 6;
        int beforeColumn = 5;
        int afterRow = 4;
        int afterColumn = 6;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }
}
