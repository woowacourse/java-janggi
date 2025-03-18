package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class 마Test {
    @ParameterizedTest
    @CsvSource({"5, 2", "5, 6", "4, 5", "4, 3", "7,2", "8,3", "8,5", "7,6"})
    void 상_하_좌_우로_한_칸_이동하고_대각선으로_한_칸_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        마 piece = new 마();
        int beforeRow = 6;
        int beforeColumn = 4;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @Test
    void 상_하_좌_우로_한_칸과_대각선으로_한_칸이_아니면_이동할_수_없다() {
        // given
        마 piece = new 마();
        int beforeRow = 6;
        int beforeColumn = 4;
        int afterRow = 4;
        int afterColumn = 4;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }


}
