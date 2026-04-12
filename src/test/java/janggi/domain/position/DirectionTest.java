package janggi.domain.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void 행이_0보다_크면_위_방향이다() {
        Direction upDirection = Direction.UP;
        boolean up = upDirection.isUp();

        Assertions.assertThat(up).isTrue();
    }

    @Test
    void 행이_0보다_작으면_아래_방향이다() {
        Direction upDirection = Direction.DOWN;
        boolean up = upDirection.isDown();

        Assertions.assertThat(up).isTrue();
    }

    @Test
    void 대각선_위_방향도_isUp이_true이다() {
        Assertions.assertThat(Direction.UP_RIGHT.isUp()).isTrue();
        Assertions.assertThat(Direction.UP_LEFT.isUp()).isTrue();
    }

    @Test
    void 대각선_아래_방향도_isDown이_true이다() {
        Assertions.assertThat(Direction.DOWN_RIGHT.isDown()).isTrue();
        Assertions.assertThat(Direction.DOWN_LEFT.isDown()).isTrue();
    }
}