package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ColumnTest {

    @Test
    void COLUMN의_범위가_8을_넘을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Column(9))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void COLUMN의_범위가_0보다_작을_경우_예외를_던진다() {
        assertThatThrownBy(() -> new Column(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
