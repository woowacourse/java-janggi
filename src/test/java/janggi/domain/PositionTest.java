package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.space.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 8})
    void x의_값은_0_이상_8_이하여야_한다(int validX) {
        int y = 0;
        assertThatCode(() -> Position.of(validX, y))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 9})
    void y의_값은_0_이상_9_이하여야_한다(int validY) {
        int x = 0;
        assertThatCode(() -> Position.of(x, validY))
                .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 10})
    void x_좌표가_0_미만이거나_8_초과이면_예외가_발생한다(int invalidX) {
        int y = 0;
        assertThatThrownBy(() -> Position.of(invalidX, y))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("범위를 벗어난 좌표입니다");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    void y_좌표가_0_미만이거나_9_초과이면_예외가_발생한다(int invalidY) {
        int x = 0;
        assertThatThrownBy(() -> Position.of(x, invalidY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("범위를 벗어난 좌표입니다");
    }
}
