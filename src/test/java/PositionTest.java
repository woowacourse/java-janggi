import janggi.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PositionTest {

    @ParameterizedTest
    @CsvSource(value = {"0,1", "1,0", "11,1", "1,10"})
    @DisplayName("유효하지 않은 범위로 위치를 생성할 수 없다.")
    void validateRange(int row, int column) {
        //given

        //when

        //then
        assertThatThrownBy(() -> Position.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 위치입니다.");
    }

    @Test
    @DisplayName("두 위치의 행 차이를 계산한다")
    void subtractRow() {
        //given
        Position position1 = Position.of(2, 1);
        Position position2 = Position.of(1, 2);

        //when
        int differenceOfRow1 = position1.subtractRow(position2);
        int differenceOfRow2 = position2.subtractRow(position1);

        //then
        assertAll(() -> {
            Assertions.assertThat(differenceOfRow1).isEqualTo(1);
            Assertions.assertThat(differenceOfRow2).isEqualTo(-1);
        });
    }

    @Test
    @DisplayName("두 위치의 열 차이를 계산한다")
    void subtractColumn() {
        //given
        Position position1 = Position.of(2, 1);
        Position position2 = Position.of(1, 2);

        //when
        int differenceOfColumn1 = position1.subtractColumn(position2);
        int differenceOfColumn2 = position2.subtractColumn(position1);

        //then
        assertAll(() -> {
            Assertions.assertThat(differenceOfColumn1).isEqualTo(-1);
            Assertions.assertThat(differenceOfColumn2).isEqualTo(1);
        });
    }
}
