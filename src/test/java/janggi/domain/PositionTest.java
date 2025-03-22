package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("검증 테스트")
    @Test
    void test1() {
        assertThatCode(() -> Position.of(1, 2))
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 좌표를 입력하면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"0,9", "11,0", "1,10", "1,0", "-1,1"}, delimiter = ',')
    void test2(int row, int column) {
        assertThatThrownBy(() -> Position.of(row, column))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
    }
}
