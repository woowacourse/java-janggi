package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantMoveStrategyTest {

    @DisplayName("상은 상하좌우 한 칸 그리고 대각선 두 칸 움직일 때의 목적지 좌표로 위치 가능하다면 true를 반환한다")
    @Test
    void test() {
        // given
        ElephantMoveStrategy elephant = new ElephantMoveStrategy();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination
                = new BoardLocation(3, 4);

        // when
        boolean isMovable = elephant.isMovable(current, destination
        );

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("상은 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        ElephantMoveStrategy elephant = new ElephantMoveStrategy();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination
                = new BoardLocation(3, 5);

        // when
        boolean isMovable = elephant.isMovable(current, destination
        );

        // then
        assertThat(isMovable).isFalse();
    }
}
