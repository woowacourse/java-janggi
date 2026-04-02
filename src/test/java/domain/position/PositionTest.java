package domain.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import common.exception.JanggiException;
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

    @ParameterizedTest
    @CsvSource({
            "0,3",
            "1,4",
            "2,5",
            "7,3",
            "8,4",
            "9,5"
    })
    void 궁성_좌표이면_true를_반환한다(int row, int column) {
        Position position = new Position(row, column);

        assertTrue(position.isInPalace());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2",
            "3,4",
            "6,4",
            "7,2",
            "9,6",
            "5,4"
    })
    void 궁성_좌표가_아니면_false를_반환한다(int row, int column) {
        Position position = new Position(row, column);

        assertFalse(position.isInPalace());
    }
}
