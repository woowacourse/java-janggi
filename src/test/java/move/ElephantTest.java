package move;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatCode;

import direction.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Elephant;
import piece.Guard;
import piece.Pieces;

class ElephantTest {

    String GREEN_ELEPHANT_EXPRESSION = "e";

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test1() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, -1);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test2() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, -1);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test3() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 0);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test4() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 4);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test5() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 5);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test6() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 5);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test7() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 0);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test8() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 4);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("직선 1칸, 대각선 2칸을 제외하고 움직일 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(8, 3);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> elephant.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로에 기물이 있을 시 이동할 수 없다.")
    void test13() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 4);
        Elephant elephant = new Elephant(GREEN_ELEPHANT_EXPRESSION, from);
        Pieces pieces = new Pieces(List.of(
                elephant,
                new Guard("r", new Point(3, 2))));

        //when
        //then
        assertThatThrownBy(() -> elephant.checkPaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
