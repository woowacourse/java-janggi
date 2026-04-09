package domain.position;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PalaceTest {

    @ParameterizedTest
    @CsvSource({
            "0,3",
            "1,4",
            "2,5",
            "7,3",
            "8,4",
            "9,5"
    })
    void 궁성_범위_안_좌표면_contains가_true를_반환한다(int row, int column) {
        Position position = new Position(row, column);

        boolean contains = Palace.findBy(position)
                .map(palace -> palace.contains(position))
                .orElse(false);

        assertTrue(contains);
    }

    @ParameterizedTest
    @CsvSource({
            "0,2",
            "3,4",
            "6,4",
            "7,6",
            "9,2"
    })
    void 궁성_범위_밖_좌표면_contains가_false를_반환한다(int row, int column) {
        Position position = new Position(row, column);

        boolean contains = Palace.findBy(position)
                .map(palace -> palace.contains(position))
                .orElse(false);

        assertFalse(contains);
    }

    @ParameterizedTest
    @CsvSource({
            "0,3,1,4",
            "1,4,2,5",
            "0,3,2,5",
            "2,5,1,4",
            "1,4,0,3",
            "2,5,0,3",
            "7,3,8,4",
            "8,4,9,5",
            "7,3,9,5",
            "9,5,8,4",
            "8,4,7,3",
            "9,5,7,3"
    })
    void 같은_궁성에서_대각선으로_연결된_좌표면_이동_가능하다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        boolean diagonalReachable = Palace.findBy(source)
                .filter(palace -> palace.contains(destination))
                .map(palace -> palace.isDiagonalReachable(source, destination))
                .orElse(false);

        assertTrue(diagonalReachable);
    }

    @ParameterizedTest
    @CsvSource({
            "0,3,0,5",
            "1,3,1,5",
            "0,4,1,5",
            "0,3,8,4",
            "2,5,7,3",
            "7,3,9,4",
            "1,4,1,4"
    })
    void 대각선_연결_규칙에_맞지_않으면_이동_불가하다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        boolean diagonalReachable = Palace.findBy(source)
                .filter(palace -> palace.contains(destination))
                .map(palace -> palace.isDiagonalReachable(source, destination))
                .orElse(false);

        assertFalse(diagonalReachable);
    }
}
