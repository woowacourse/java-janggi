package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessPositionTest {
    @DisplayName("(0, 0) ~ (9, 8) 이외의 위치는 생성할 수 없다.")
    @ParameterizedTest
    @CsvSource({"-1, 0", "0, -1", "9, 9", "10, 8"})
    void outOfBoundPosition(final int row, final int col) {
        // given

        // when & then
        assertThatThrownBy(() -> {
            new ChessPosition(row, col);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
