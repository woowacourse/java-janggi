package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 9",
            "0, 8",
            "8, 9",
            "8, 8",
            "2, 3"
    })
    void 정상_생성_테스트(int col, int row) {
        Assertions.assertThat(new Position(col, row)).isInstanceOf(Position.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 9, 12})
    void X_예외_값_입력_오류_검증(int col) {
        assertThatThrownBy(() -> new Position(col, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]", "col 좌표는");
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 10, 12, 1000})
    void Y_예외_값_입력_오류_검증(int row) {
        assertThatThrownBy(() -> new Position(1, row))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]", "y좌표");
    }
}
