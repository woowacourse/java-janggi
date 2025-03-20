package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.strategy.ChoPawnMoveStrategy;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoPawnMoveStrategyTest {
    private ChoPawnMoveStrategy pawn = new ChoPawnMoveStrategy();

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 2);
        BoardLocation destination = new BoardLocation(1, 1);

        // when
        boolean isMovable = pawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 3);
        BoardLocation destination = new BoardLocation(1, 1);

        // when
        boolean isMovable = pawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 하방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(3, 2);
        BoardLocation destination = new BoardLocation(3, 3);

        // when
        boolean isMovable = pawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("졸 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test4() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 1);

        // when
        List<BoardLocation> allPath = pawn.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of());
    }


}
