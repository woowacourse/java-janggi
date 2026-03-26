package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChoTurnTest {

    @DisplayName("한나라 턴을 반환한다.")
    @Test
    void play() {
        assertThat(new ChoTurn().play())
                .isInstanceOf(HanTurn.class);
    }

    @DisplayName("게임이 아직 끝나지 않았다.")
    @Test
    void isGameOver() {
        assertThat(new ChoTurn().isGameOver())
                .isFalse();
    }
}