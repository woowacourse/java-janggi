package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MovementTest {
    @ParameterizedTest
    @CsvSource({
            "-1, 0, UP",
            "1, 0, DOWN",
            "0, 1, RIGHT",
            "0, -1, LEFT",
            "-1, 1, UP_RIGHT",
            "-1, -1, UP_LEFT",
            "1, 1, DOWN_RIGHT",
            "1, -1, DOWN_LEFT"
    })
    void Movement_객체를_정상적으로_반환한다(int dx, int dy, Movement expected) {
        assertThat(Movement.of(dx, dy)).isEqualTo(expected);
    }

    @Test
    void 정의되지_않은_dx_dy에_대해서_예외가_발생한다 () {
        assertThatThrownBy(() -> Movement.of(2, 2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당 dx,dy에 대한 movement가 없습니다.");
    }
}
