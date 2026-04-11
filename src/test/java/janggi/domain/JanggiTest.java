package janggi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JanggiTest {


    @Test
    void 자신의_턴일때_상대의_기물을_선택하면_예외_처리한다() {
        Janggi janggi = Janggi.start(1, 1);

        Assertions.assertThatThrownBy(() -> janggi.validateCamp(Position.of(9, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 기물만 선택할 수 있습니다.");
    }
}
