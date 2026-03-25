package janggi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @DisplayName("위로 이동한다.")
    @Test
    void previous() {
        Row row = Row.TWO;
        assertThat(row.previous())
                .isEqualTo(Row.ONE);
    }

    @DisplayName("1번 행에서 위로 이동하면 OUT으로 간다.")
    @Test
    void previous_OUT() {
        Row row = Row.ONE;
        assertThat(row.previous())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("OUT에서 위로 이동하면 여전히 OUT이다.")
    @Test
    void previous_OUT_from_OUT() {
        Row row = Row.OUT;
        assertThat(row.previous())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("아래로 이동한다.")
    @Test
    void next() {
        Row row = Row.TWO;
        assertThat(row.next())
                .isEqualTo(Row.THREE);
    }

    @DisplayName("0번 행에서 아래로 이동하면 OUT으로 간다.")
    @Test
    void next_OUT() {
        Row row = Row.ZERO;
        assertThat(row.next())
                .isEqualTo(Row.OUT);
    }

    @DisplayName("OUT에서 아래로 이동하면 여전히 OUT이다.")
    @Test
    void next_OUT_from_OUT() {
        Row row = Row.OUT;
        assertThat(row.next())
                .isEqualTo(Row.OUT);
    }
}