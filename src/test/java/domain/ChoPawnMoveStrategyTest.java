package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.strategy.ChoPawnMoveStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoPawnMoveStrategyTest {
    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        ChoPawnMoveStrategy pawn = new ChoPawnMoveStrategy();
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
        ChoPawnMoveStrategy pawn = new ChoPawnMoveStrategy();
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
        ChoPawnMoveStrategy pawn = new ChoPawnMoveStrategy();
        BoardLocation current = new BoardLocation(3, 2);
        BoardLocation destination = new BoardLocation(3, 3);

        // when
        boolean isMovable = pawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

}
