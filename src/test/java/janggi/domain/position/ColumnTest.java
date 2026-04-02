package janggi.domain.position;


import janggi.exception.business.ColumnOutOfRangeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ColumnTest {
    @Test
    void 장기판의_y좌표_범위를_벗어나면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Column.of(9)).isInstanceOf(ColumnOutOfRangeException.class);
        Assertions.assertThatThrownBy(() -> Column.of(-1)).isInstanceOf(ColumnOutOfRangeException.class);
    }

    @Test
    void 장기판의_y좌표_범위_내라면_예외가_발생하지_않는다() {
        Column column1 = Column.of(0);
        Column column2 = Column.of(8);

        Assertions.assertThat(column1.getColumn()).isEqualTo(0);
        Assertions.assertThat(column2.getColumn()).isEqualTo(8);
    }

    @Test
    void 동등성_테스트() {
        Column column = Column.of(1);

        Assertions.assertThat(column).isEqualTo(Column.of(1));
    }
}
