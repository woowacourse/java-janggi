package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HanTurnTest {

    @DisplayName("초나라 턴을 반환한다.")
    @Test
    void play() {
        assertThat(new HanTurn().play())
                .isInstanceOf(ChoTurn.class);
    }

    @DisplayName("게임이 아직 끝나지 않았다.")
    @Test
    void isGameOver() {
        assertThat(new HanTurn().isGameOver())
                .isFalse();
    }
}