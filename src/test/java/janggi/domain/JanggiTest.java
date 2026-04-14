package janggi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiTest {


    @Test
    void 자신의_턴일때_상대의_기물을_선택하면_예외_처리한다() {
        Janggi janggi = Janggi.start(1, 1);

        Assertions.assertThatThrownBy(() -> janggi.validateCamp(JanggiPosition.of(9, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 기물만 선택할 수 있습니다.");
    }

    @DisplayName("무승부를 요청하면 게임 진행 상태가 false로 변경된다.")
    @Test
    void drawGame_EndsOngoingState() {
        Janggi janggi = Janggi.start(1, 1);
        assertThat(janggi.isOnGoing()).isTrue();

        janggi.drawGame();

        assertThat(janggi.isOnGoing()).isFalse();
    }
}
