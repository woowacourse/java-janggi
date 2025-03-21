package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PointTest {

    @ParameterizedTest
    @CsvSource({
            "1,1", "4,3", "8,9", "0,0"
    })
    void 올바른_좌표를_입력_받으면_true_반환(final String x, final String y) {

        // given
        // when
        // then
        assertThatCode(() -> Point.of(x, y))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({
            "-1,-1", "10,11", "9,0", "야,뭐", "y,n"
    })
    void 올바르지_않은_좌료를_입력_받으면_false_반환(final String x, final String y) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Point.of(x, y))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
