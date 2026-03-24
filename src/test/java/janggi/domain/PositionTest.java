package janggi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    @DisplayName("포지션의 행이 0~9행이 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "-1,8",
            "10,1",
    })
    void 포지션_행_예외_테스트(int row, int col) {
        Assertions.assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 행은 0행 이상 9행 이하여야 합니다.");
    }

    @DisplayName("포지션의 열이 0~8열이 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0,9",
            "0,-1",
    })
    void 포지션_열_예외_테스트(int row, int col) {
        Assertions.assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 열은 0열 이상 8열 이하여야 합니다.");
    }
}
