package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    void ROW의_범위가_9를_넘을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Row(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ROW의_범위가_0보다_작을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Row(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
