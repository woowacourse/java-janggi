package piece;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatCode;

import game.Team;
import location.PathManagerImpl;
import location.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(5, 5);
        Position to = new Position(3, 2);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test2() {
        //given
        Position from = new Position(4, 4);
        Position to = new Position(6, 1);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test3() {
        //given
        Position from = new Position(5, 5);
        Position to = new Position(2, 3);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test4() {
        //given
        Position from = new Position(5, 5);
        Position to = new Position(2, 7);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test5() {
        //given
        Position from = new Position(5, 5);
        Position to = new Position(3, 8);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test6() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(4, 5);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test7() {
        //given
        Position from = new Position(5, 5);
        Position to = new Position(8, 3);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test8() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(5, 4);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> elephant.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("직선 1칸, 대각선 2칸을 제외하고 움직일 수 없다.")
    void test9() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(8, 3);
        Elephant elephant = new Elephant(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> elephant.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로에 기물이 있을 시 이동할 수 없다.")
    void test13() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(5, 4);
        Elephant elephant = new Elephant(1, Team.GREEN, from);
        Pieces pieces = new Pieces(List.of(
                elephant,
                new Guard(2, Team.GREEN, new PathManagerImpl(), new Position(3, 2))));

        //when

        //then
        assertThatThrownBy(() -> elephant.validatePaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
