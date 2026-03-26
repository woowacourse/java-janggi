package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
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

    @Test
    public void 기존_열에_값을_더해서_새로운_열을_만든다() {
        // given
        int columnNum = 1;
        Column column = new Column(1);

        // when
        Column result = column.add(columnNum);

        // then
        assertThat(result).isEqualTo(new Column(2));
    }

}
