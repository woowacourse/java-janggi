package move;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseMovementTest {
    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(1, 0);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void test2() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(3, 0);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void test3() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(0, 1);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void test4() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(0, 3);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void test5() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(1, 4);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void test6() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(3, 4);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void test7() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(4, 1);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void test8() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(4, 3);
        HorseMovement horseMovement = new HorseMovement();

        //when
        Position result = horseMovement.move(from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 1칸을 제외하고 움직일 수 없다.")
    void test9() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(6, 3);
        HorseMovement horseMovement = new HorseMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> horseMovement.move(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
