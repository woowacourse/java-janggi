package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    public void 행의_값은_1부터_10까지의_숫자이다(int row) {
        // when & then
        assertThatCode(() -> new Row(row))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 11})
    public void 행의_값이_1붙터_10까지_숫자가_아니면_예외가_발생한다(int row) {
        // when & then
        assertThatThrownBy(() -> new Row(row))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
