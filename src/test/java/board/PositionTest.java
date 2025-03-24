package board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @CsvSource(value = {"1,1", "1,10", "9,1", "9,10"})
    @ParameterizedTest
    void 위치는_장기판_내부에_존재해야_한다(int row, int column) {
        assertDoesNotThrow(() -> new Position(row, column));
    }

}
