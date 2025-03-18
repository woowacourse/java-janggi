package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {

    @ParameterizedTest
    @ValueSource(ints = {9, -1})
    @DisplayName("장기 열의 범위를 넘어가면 예외가 발생한다")
    void createColumnException(int column) {
        // when & then
        assertThatThrownBy(() -> new Column(column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판 열의 범위를 벗어났습니다.");
    }

}
