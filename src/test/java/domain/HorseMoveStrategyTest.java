package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseMoveStrategyTest {

    @DisplayName("마는 상하좌우 한 칸 그리고 대각선 한 칸 움직일 때의 목적지 좌표로 위치 가능하다면 true를 반환한다")
    @Test
    void test() {
        // given
        HorseMoveStrategy horse = new HorseMoveStrategy();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation target = new BoardLocation(2, 3);

        // when
        boolean isMovable = horse.isMovable(current, target);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("차(車)는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        // given
        HorseMoveStrategy horse = new HorseMoveStrategy();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation target = new BoardLocation(3, 3);

        // when
        boolean isMovable = horse.isMovable(current, target);

        // then
        assertThat(isMovable).isFalse();
    }
}
