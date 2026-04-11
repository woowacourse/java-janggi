package domain.position;

import common.exception.JanggiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

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
            "1, 4, 0, 3, 1, 1",
            "1, 4, 3, 6, 2, 2",
            "0, 0, 5, 5, 5, 5"
    })
    void 두_좌표의_행과_열_차이가_같으면_대각선_관계이다(
            int sourceRow, int sourceColumn,
            int destinationRow, int destinationColumn,
            int expectedRowDiff, int expectedColDiff
    ) {
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        assertEquals(expectedRowDiff, source.rowDiff(destination));
        assertEquals(expectedColDiff, source.colDiff(destination));
        assertTrue(source.isDiagonalWith(destination));
        assertTrue(destination.isDiagonalWith(source));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, 1, 3",
            "1, 4, 2, 4",
            "1, 4, 3, 5",
            "0, 0, 1, 2"
    })
    void 두_좌표의_행과_열_차이가_다르면_대각선_관계가_아니다(
            int sourceRow, int sourceColumn,
            int destinationRow, int destinationColumn
    ) {

        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        assertFalse(source.isDiagonalWith(destination));
    }

    @Test
    void 자기_자신과의_거리는_0이고_대각선_관계이다() {
        Position position = new Position(4, 4);

        assertEquals(0, position.rowDiff(position));
        assertEquals(0, position.colDiff(position));
        assertTrue(position.isDiagonalWith(position));
    }
}
