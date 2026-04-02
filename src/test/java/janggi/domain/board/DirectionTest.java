package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @DisplayName("해당 방향으로 한 칸 이동 시 보드 범위를 이탈하지 않는지 확인한다")
    @Test
    void canMove_ReturnsTrueIfNextPositionIsValid() {
        Position topEdge = new Position(0, 0);

        assertThat(Direction.S.canMove(topEdge)).isTrue();
        assertThat(Direction.N.canMove(topEdge)).isFalse();
    }

    @DisplayName("현재 위치에서 해당 방향으로 이동한 새로운 좌표를 반환한다")
    @Test
    void move_ReturnsNextPosition() {
        Position current = new Position(5, 5);

        Position next = Direction.NE.move(current);

        assertThat(next).isEqualTo(new Position(4, 6));
    }
}
