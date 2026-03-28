import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8})
    void x의_값은_0_이상_8_이하여야_한다(int value) {
        int y = 0;
        assertThatCode(() -> new Position(value, y))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void y의_값은_0_이상_9_이하여야_한다(int value) {
        int x = 0;
        assertThatCode(() -> new Position(x, value))
                .doesNotThrowAnyException();
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, -2, 9, 10, 100})
    void x의_값이_0_이상_8_이하가_아니면_예외가_발생한다(int value) {
        int y = 0;
        assertThatThrownBy(() -> new Position(value, y))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, 10, 11, 100})
    void y의_값이_0_이상_9_이하가_아니면_예외가_발생한다(int value) {
        int x = 0;
        assertThatThrownBy(() -> new Position(x, value))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
