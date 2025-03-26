package domain.spatial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatCode;

class VectorTest {

    @ParameterizedTest
    @CsvSource({
            "-2, 0",
            "2, 0",
            "0, -2",
            "0, 2"
    })
    void 이동_수치가_아닌_값으로_객체_생성시_예외가_발생한다(int moveRow, int moveColumn) {
        assertThatCode(() -> new Vector(moveRow, moveColumn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 방향의 수치는 -1, 0, 1만 가능합니다.");
    }
}


