package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 행이_9를_초과하면_예외를_던진다() {
        // When & Then
        Assertions.assertThatThrownBy(() -> new Position(10, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이동입니다.");
    }

    @Test
    void 열이_8을_초과하면_예외를_던진다() {
        // When & Then
        Assertions.assertThatThrownBy(() -> new Position(0, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이동입니다.");
    }
}
