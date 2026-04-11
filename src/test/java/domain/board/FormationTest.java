package domain.board;

import common.exception.JanggiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FormationTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1,SANG_MA_SANG_MA",
            "2,MA_SANG_MA_SANG",
            "3,MA_SANG_SANG_MA",
            "4,SANG_MA_MA_SANG"})
    void 숫자_1이상_4이하로_포메이션_생성이_가능하다(int number, Formation expected) {
        Formation formation = Formation.from(number);

        assertEquals(expected, formation);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "5", "6"})
    void 숫자_1이상_4이하로_포메이션_생성이_가능하다(int number) {
        assertThrows(JanggiException.class, () -> Formation.from(number));
    }
}
