package janggi;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolTest {

    @DisplayName("졸은 뒤로 움직일 수 없다.")
    @Test
    void shouldThrowException_WhenMoveBackward() {
        // given
        Jol jol = new Jol();
        Point fromPoint = new Point(0, 2);
        Point toPoint = new Point(0, 1);

        // when & then
        assertThatCode(() -> jol.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("졸은 뒤로 갈 수 없습니다.");
    }

    @DisplayName("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @Test
    void shouldThrowException_WhenMove2Point() {
        // given
        Jol jol = new Jol();
        Point fromPoint = new Point(2, 1);
        Point toPoint = new Point(0, 1);

        // when & then
        assertThatCode(() -> jol.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("졸은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }
}
