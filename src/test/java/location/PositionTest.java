package location;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {
    @Test
    @DisplayName("좌표에 방향을 적용할 수 있다.")
    void test1() {
        //given
        Position position = new Position(2, 2);
        Direction direction = Direction.LEFT;

        //when
        Position positionAppliedDirection = position.apply(direction);

        //then
        assertThat(positionAppliedDirection.x()).isEqualTo(position.x() + direction.getX());
        assertThat(positionAppliedDirection.y()).isEqualTo(position.y() + direction.getY());
    }

    @Test
    @DisplayName("출발지와 목적지가 동일한 좌표일 경우 예외가 발생한다.")
    void test2() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 2);

        //when & then
        assertThatThrownBy(() -> from.validateNotSame(to)).isInstanceOf(IllegalArgumentException.class);
    }
}
