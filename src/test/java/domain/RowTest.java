package domain;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {10, 11, -1})
    @DisplayName("장기 행의 범위를 넘어가면 예외가 발생한다")
    void createRowException(int row) {
        // when & then
        assertThatThrownBy(() -> new Row(row))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판 행의 범위를 벗어났습니다.");
    }
}
