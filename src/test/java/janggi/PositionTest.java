package janggi;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    @ParameterizedTest
    @DisplayName("장기판 밖의 위치를 지정할 수 없다.")
    @CsvSource(value = {"0,1", "1, 0", "10, 1", "1, 11"})
    void shouldThrowExceptionWhenOutsideOfBoard(int x, int y) {
        assertThatThrownBy(() -> new Position(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
