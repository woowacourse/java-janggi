package domain.spatial;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
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

    @ParameterizedTest
    @CsvSource({
            "0, 0, false",
            "1, 0, false",
            "-1, 0, false",
            "0, 1, false",
            "0, -1, false",
            "1, 1, true",
            "1, -1, true",
            "-1, 1, true",
            "-1, -1, true"
    })
    void 이동_방향이_대각선인지_판단한다(int moveRow, int moveColumn, boolean expected) {
        // given
        Vector vector = new Vector(moveRow, moveColumn);

        // when
        boolean result = vector.isDiagonal();

        // then
        assertThat(result).isEqualTo(expected);
    }
}


