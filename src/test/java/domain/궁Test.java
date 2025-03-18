package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class 궁Test {

    @ParameterizedTest
    @CsvSource({"8, 5", "9, 4", "9, 6", "0, 5"})
    void 상_하_좌_우로_한_칸_이동할_수_있다(int afterRow, int afterColumn) {
        // given
        궁 piece = new 궁();
        int beforeRow = 9;
        int beforeColumn = 5;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @Test
    void 상_하_좌_우로_한_칸이_아닌_경우_이동할_수_없다() {
        // given
        궁 piece = new 궁();
        int beforeRow = 9;
        int beforeColumn = 5;
        int afterRow = 8;
        int afterColumn = 4;

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }
}
