package janggi.domain.piece;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    @DisplayName("시작점에서 오른쪽으로 도달할 수 있는 도착점은 true를 반환한다.")
    void canRightReach() {
        // given
        int startX = 0;
        int startY = 0;
        int endX = 3;
        int endY = 0;

        // when
        Cha cha = new Cha();

        // then
        Assertions.assertThat(cha.canMove(startX, startY, endX, endY)).isTrue();
    }

    @Test
    @DisplayName("시작점에서 왼쪽으로 도달할 수 있는 도착점은 true를 반환한다.")
    void canLeftReach() {
        // given
        int startX = 2;
        int startY = 0;
        int endX = 1;
        int endY = 0;

        // when
        Cha cha = new Cha();

        // then
        Assertions.assertThat(cha.canMove(startX, startY, endX, endY)).isTrue();
    }

    @Test
    @DisplayName("시작점에서 위로 도달할 수 있는 도착점은 true를 반환한다.")
    void canUpReach() {
        // given
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 1;

        // when
        Cha cha = new Cha();

        // then
        Assertions.assertThat(cha.canMove(startX, startY, endX, endY)).isTrue();
    }

    @Test
    @DisplayName("시작점에서 아래로 도달할 수 있는 도착점은 true를 반환한다.")
    void canDownReach() {
        // given
        int startX = 0;
        int startY = 1;
        int endX = 0;
        int endY = 0;

        // when
        Cha cha = new Cha();

        // then
        Assertions.assertThat(cha.canMove(startX, startY, endX, endY)).isTrue();
    }

}
