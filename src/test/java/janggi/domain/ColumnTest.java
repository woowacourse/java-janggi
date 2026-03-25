package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.position.Column;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 9})
    public void 열의_값은_1부터_9까지의_숫자이다(int column) {
        // when & then
        assertThatCode(() -> new Column(column))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 10})
    public void 열의_값이_1부터_9까지_숫자가_아니면_예외가_발생한다(int column) {
        // when & then
        assertThatThrownBy(() -> new Column(column))
                .isInstanceOf(IllegalArgumentException.class);
    }

}