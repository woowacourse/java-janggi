package move;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StraightLineMovementTest {
    @Test
    @DisplayName("수평 방향으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(10, 2);
        StraightLineMovement straightLineMovement = new StraightLineMovement();

        //when
        Position result = straightLineMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("수직 방향으로 이동할 수 있다.")
    void test2() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 10);
        StraightLineMovement straightLineMovement = new StraightLineMovement();

        //when
        Position result = straightLineMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(6, 3);
        StraightLineMovement straightLineMovement = new StraightLineMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> straightLineMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void test10() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 2);
        StraightLineMovement straightLineMovement = new StraightLineMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> straightLineMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
