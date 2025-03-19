package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @CsvSource(value = {"LEFT,-1,0", "RIGHT,1,0", "TOP,0,1", "BOTTOM,0,-1"})
    @ParameterizedTest
    void 방향은_위_아래_왼쪽_오른쪽이_있다(Direction direction, int DRow, int DColumn) {
        Assertions.assertThat(direction.getDeltaRow()).isEqualTo(DRow);
        Assertions.assertThat(direction.getDeltaColumn()).isEqualTo(DColumn);
    }
}
