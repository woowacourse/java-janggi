package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class 포Test {

    @ParameterizedTest
    @CsvSource({"5, 5, 3, 5", "7, 7, 7, 9", "7, 3, 7, 1", "8, 5, 9, 5"})
    void 수직_수평으로_장애물을_넘어_이동할_수_있다(int hurdleRow, int hurdleColumn, int afterRow, int afterColumn) {
        // given
        포 piece = new 포();
        int beforeRow = 7;
        int beforeColumn = 5;
        piece.setHurdle(hurdleRow, hurdleColumn);

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isTrue();
    }

    @ParameterizedTest
    @CsvSource({"8, 5, 6, 5", "7, 6, 7, 2"})
    void 장애물을_넘어_이동하지_않는_경우_이동할_수_없다(int hurdleRow, int hurdleColumn, int afterRow, int afterColumn) {
        // given
        포 piece = new 포();
        int beforeRow = 7;
        int beforeColumn = 5;
        piece.setHurdle(hurdleRow, hurdleColumn);

        // when & then
        Assertions.assertThat(piece.canMove(beforeRow, beforeColumn, afterRow, afterColumn))
                .isFalse();
    }

}
