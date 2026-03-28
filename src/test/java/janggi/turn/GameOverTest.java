package janggi.turn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.model.turn.GameOver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameOverTest {

    @DisplayName("게임 종료 시 턴을 수행하고자 하면 예외 발생한다.")
    @Test
    void play() {
        assertThatThrownBy(
                () -> new GameOver().play(new Position(Row.THREE, Column.EIGHT), new Position(Row.FIVE, Column.EIGHT)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @DisplayName("게임 종료된 상태이면 true를 반환한다.")
    @Test
    void isGameOver() {
        assertThat(new GameOver().isGameOver())
                .isTrue();
    }
}
