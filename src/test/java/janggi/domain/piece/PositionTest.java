package janggi.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {
    @DisplayName("보드를 벗어나는 위치 값이 생성되면 예외를 발생시킨다.")
    @CsvSource(value = {"11,0", "-1,0", "0,10"})
    @ParameterizedTest
    void movableValidationTest(final int x, final int y) {
        assertThatThrownBy(() -> new Position(x, y)).isInstanceOf(IllegalArgumentException.class);
    }
}