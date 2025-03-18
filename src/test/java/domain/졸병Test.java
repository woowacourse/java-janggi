package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class 졸병Test {
    @ParameterizedTest
    @CsvSource({"6, 5", "7, 4", "7, 6"})
    void 졸은_상_좌_우로_한_칸_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        졸병 piece = new 졸병(Side.초);
        int beforeRow = 7;
        int beforeColumn = 5;

        // when & then
        assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @Test
    void 졸은_상_좌_우로_한_칸이_아닌_경우_이동할_수_없다() {
        // given
        졸병 piece = new 졸병(Side.초);
        int beforeRow = 7;
        int beforeColumn = 5;
        int afterRow = 6;
        int afterColumn = 4;

        // when & then
        assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }

    @ParameterizedTest
    @CsvSource({"3, 4", "3, 6", "4, 5"})
    void 병은_상_좌_우로_한_칸_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        졸병 piece = new 졸병(Side.한);
        int beforeRow = 3;
        int beforeColumn = 5;

        // when & then
        assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @Test
    void 병은_상_좌_우로_한_칸이_아닌_경우_이동할_수_없다() {
        // given
        졸병 piece = new 졸병(Side.한);
        int beforeRow = 3;
        int beforeColumn = 5;
        int afterRow = 2;
        int afterColumn = 5;

        // when & then
        assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }
}
