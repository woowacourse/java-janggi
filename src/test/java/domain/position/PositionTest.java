package domain.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.JanggiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource(value = {
            "0,0",
            "0,8",
            "9,0",
            "9,8",
            "5,4",
            "1,1",
            "8,7"
    })
    void Row가_0이상_9이하_Column이_0이상_8이하이면_포지션이_정상적으로_생성된다(int row, int column) {
        Position position = new Position(row, column);

        assertEquals(column, position.column());
    }

    @ParameterizedTest
    @CsvSource({
            "-1,0",
            "0,9",
            "0,-1",
            "10,0",
            "-1,-1",
            "10,9",
            "10,8",
            "9,9"
    })
    void Row가_0미만_9초과_Column이_0미만_8초과이면_예외가_발생한다(int row, int column) {
        assertThrows(JanggiException.class, () -> new Position(row, column));
    }
}
