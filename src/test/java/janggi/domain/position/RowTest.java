package janggi.domain.position;

import janggi.exception.business.RowOutOfRangeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RowTest {
    @Test
    void 장기판의_x좌표_범위를_벗어나면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Row.of(10)).isInstanceOf(RowOutOfRangeException.class);
        Assertions.assertThatThrownBy(() -> Row.of(-1)).isInstanceOf(RowOutOfRangeException.class);
    }

    @Test
    void 장기판의_x좌표_범위_내라면_예외가_발생하지_않는다() {
        Row row1 = Row.of(0);
        Row row2 = Row.of(9);

        Assertions.assertThat(row1.getRow()).isEqualTo(0);
        Assertions.assertThat(row2.getRow()).isEqualTo(9);
    }

    @Test
    void 동등성_테스트() {
        Row row = Row.of(1);

        Assertions.assertThat(row).isEqualTo(Row.of(1));
    }
}
