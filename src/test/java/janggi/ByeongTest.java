package janggi;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @DisplayName("병은 뒤로 움직일 수 없다.")
    @Test
    void shouldThrowException_WhenMoveBackward() {
        // given
        Byeong byeong = new Byeong();
        Point fromPoint = new Point(0, 1);
        Point toPoint = new Point(0, 2);

        // when & then
        assertThatCode(() -> byeong.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 뒤로 갈 수 없습니다.");
    }

    @DisplayName("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.")
    @Test
    void shouldThrowException_WhenMove2Point() {
        // given
        Byeong byeong = new Byeong();
        Point fromPoint = new Point(0, 1);
        Point toPoint = new Point(2, 1);

        // when & then
        assertThatCode(() -> byeong.validateMove(fromPoint, toPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
    }
}
