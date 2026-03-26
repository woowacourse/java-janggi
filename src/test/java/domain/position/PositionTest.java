package domain.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @ParameterizedTest
    @CsvSource(value = {
            "0,0",
            "8,0",
            "0,9",
            "8,9",
            "4,5",
            "1,1",
            "7,8"
    })
    void X가_0이상_8이하_Y가_0이상_9이하이면_포지션이_정상적으로_생성된다(int x, int y) {
        Position position = new Position(x, y);

        assertEquals(x, position.getX());
    }

    @ParameterizedTest
    @CsvSource({
            "-1,0",
            "9,0",
            "0,-1",
            "0,10",
            "-1,-1",
            "9,10",
            "8,10",
            "9,9"
    })
    void X가_0미만_8초과_Y가_0미만_9초과이면_예외가_발생한다(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> new Position(x, y));
    }
}
