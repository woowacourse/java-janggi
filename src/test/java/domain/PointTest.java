package domain;

import domain.board.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 11, 100})
    void 정해진_행의_범위를_벗어나면_예외가_발생한다(final int row) {
        // given
        final int column = 1;
        // when & then
        Assertions.assertThatThrownBy(() -> Point.of(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 100})
    void 정해진_열의_범위를_벗어나면_예외가_발생한다(final int column) {
        // given
        final int row = 1;
        // when & then
        Assertions.assertThatThrownBy(() -> Point.of(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }
}