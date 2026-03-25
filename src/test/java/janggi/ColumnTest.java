package janggi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @DisplayName("오른쪽으로 이동한다.")
    @Test
    void next() {
        Column column = Column.TWO;
        assertThat(column.next())
                .isEqualTo(Column.THREE);
    }

    @DisplayName("9번 열에서 오른쪽으로 이동하면 OUT으로 간다.")
    @Test
    void next_OUT() {
        Column column = Column.NINE;
        assertThat(column.next())
                .isEqualTo(Column.OUT);
    }

    @DisplayName("OUT에서 오른쪽으로 이동하면 OUT으로 간다.")
    @Test
    void next_OUT_from_OUT() {
        Column column = Column.OUT;
        assertThat(column.next())
                .isEqualTo(Column.OUT);
    }

    @DisplayName("왼쪽으로 이동한다.")
    @Test
    void previous() {
        Column column = Column.TWO;
        assertThat(column.previous())
                .isEqualTo(Column.ONE);
    }

    @DisplayName("1번 열에서 왼쪽으로 이동하면 OUT으로 간다.")
    @Test
    void previous_OUT() {
        Column column = Column.ONE;
        assertThat(column.previous())
                .isEqualTo(Column.OUT);
    }

    @DisplayName("OUT에서 왼쪽으로 이동하면 OUT으로 간다.")
    @Test
    void previous_OUT_from_OUT() {
        Column column = Column.OUT;
        assertThat(column.previous())
                .isEqualTo(Column.OUT);
    }
}