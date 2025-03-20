package janggiGame.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class DotTest {
    @DisplayName("x와 y 좌표를 가지고 있는 점을 생성한다.")
    @Test
    void createPosition() {
        // given
        int x = 1;
        int y = 2;

        // when // then
        assertThatCode(() -> new Dot(x, y))
                .doesNotThrowAnyException();

    }
}