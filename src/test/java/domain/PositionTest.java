package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "3, 10",
            "2, -1"
    })
    @DisplayName("열의 범위를 넘어가면 예외가 발생한다")
    void createColumnPositionException(int row, int col) {
        // given

        // when & then
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판 열의 범위를 벗어났습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "11, 3",
            "-10, 2"
    })
    @DisplayName("행의 범위를 넘어가면 예외가 발생한다")
    void createRowPositionException(int row, int col) {
        // given

        // when & then
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판 행의 범위를 벗어났습니다.");
    }

}
