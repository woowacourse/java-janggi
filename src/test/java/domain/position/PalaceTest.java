package domain.position;

import domain.direction.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PalaceTest {

    @Test
    @DisplayName("해당 위치가 궁성영역인지 아닌지 판단할 수 있다.")
    void isPalace_궁성영역_판단_성공테스트() {
        Position palacePosition = Position.of(1, 4);

        boolean result = Palace.isPalace(palacePosition);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("해당 위치가 궁성영역인지 아닌지 판단할 수 있다.")
    void isPalace_궁성영역_판단_실패테스트() {
        Position palacePosition = Position.of(1, 3);

        boolean result = Palace.isPalace(palacePosition);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성영역 중 해당 위치에서 이동할 수 있는 방향을 가지고 있다면 이동이 가능하다.")
    void canMoveDiagonallyPosition_궁성영역_이동가능방향_판단_테스트1(){
        Position palacePosition = Position.of(1, 4);
        Direction direction = Direction.UP_RIGHT;

        boolean result = Palace.canMoveDiagonallyPosition(palacePosition, direction);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성영역 중 해당 위치에서 이동할 수 없는 방향을 가지고 있다면 이동이 불가능하다.")
    void canMoveDiagonallyPosition_궁성영역_이동가능방향_판단_테스트2(){
        Position palacePosition = Position.of(1, 4);
        Direction direction = Direction.UP_LEFT;

        boolean result = Palace.canMoveDiagonallyPosition(palacePosition, direction);

        assertThat(result).isFalse();
    }
}
