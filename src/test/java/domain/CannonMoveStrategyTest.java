package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.strategy.CannonMoveStrategy;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonMoveStrategyTest {

    private CannonMoveStrategy cannon = new CannonMoveStrategy();

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination
                = new BoardLocation(1, 2);

        // when
        boolean isMovable = cannon.isMovable(current, destination
        );

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        CannonMoveStrategy cannon = new CannonMoveStrategy();
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination
                = new BoardLocation(2, 2);

        // when
        boolean isMovable = cannon.isMovable(current, destination
        );

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("차 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(4, 1);

        // when
        List<BoardLocation> allPath = cannon.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(2,1), new BoardLocation(3,1), destination));
    }
}
