package domain.coordination;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoordinationTest {

    @ParameterizedTest
    @CsvSource(value = {
            "5,9,4,8",
            "5,9,4,10",
            "5,9,6,8",
            "5,9,6,10",
            "4,8,5,9",
            "4,10,5,9",
            "6,8,5,9",
            "6,10,5,9",
            "5,2,4,1",
            "5,2,4,3",
            "5,2,6,1",
            "5,2,6,3",
            "4,1,5,2",
            "4,3,5,2",
            "6,1,5,2",
            "6,3,5,2",
            "1,1,2,2",
            "3,5,4,6",
            "7,3,8,4",
    })
    public void 궁성_내_대각선_1칸_이동시_중간_경로가_비어있다(int fromColumn, int fromRow, int toColumn, int toRow) {
        Coordination from = Coordination.of(fromColumn, fromRow);
        Coordination to = Coordination.of(toColumn, toRow);

        assertThat(from.diagonalPathTo(to)).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8,6,10,1",
            "4,10,6,8,1",
            "6,8,4,10,1",
            "6,10,4,8,1",
            "4,1,6,3,1",
            "4,3,6,1,1",
            "6,1,4,3,1",
            "6,3,4,1,1",
            "1,1,4,4,2",
            "2,5,5,2,2",
            "1,1,5,5,3",
            "1,5,5,1,3"
    })
    public void 대각선_이동시_중간_경로의_개수를_반환한다(int fromColumn, int fromRow, int toColumn, int toRow, int expectedSize) {
        Coordination from = Coordination.of(fromColumn, fromRow);
        Coordination to = Coordination.of(toColumn, toRow);

        assertThat(from.diagonalPathTo(to)).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource(value = {"5,3", "3,5", "5,7"})
    public void 대각선이_아닌_좌표로_diagonalPathTo_호출시_예외가_발생한다(int column, int row) {
        Coordination from = Coordination.of(5, 5);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> from.diagonalPathTo(to))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
