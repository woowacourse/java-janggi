package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "1, 9",
            "9, 0",
            "9, 9",
            "3, 4"
    })
    void 정상_생성_테스트(int x, int y) {
        Assertions.assertThat(new Position(x, y)).isInstanceOf(Position.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10, 12})
    void X_예외_값_입력_오류_검증(int x) {
        assertThatThrownBy(() -> new Position(x, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]", "x 좌표는");
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 10, 12, 1000})
    void Y_예외_값_입력_오류_검증(int y) {
        assertThatThrownBy(() -> new Position(1, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]", "y좌표");
    }
}
