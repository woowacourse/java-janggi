package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.strategy.HorseMoveStrategy;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseMoveStrategyTest {

    private HorseMoveStrategy horse = new HorseMoveStrategy();

    @DisplayName("마는 상하좌우 한 칸 그리고 대각선 한 칸 움직일 때의 목적지 좌표로 위치 가능하다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 3);

        // when
        boolean isMovable = horse.isMovable(current, destination);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("마는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(3, 3);

        // when
        boolean isMovable = horse.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 3);

        // when
        List<BoardLocation> allPath = horse.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(1,2)));
    }
}
