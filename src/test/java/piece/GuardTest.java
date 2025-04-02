package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import game.Team;
import location.PathManager;
import location.PathManagerImpl;
import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GuardTest {

    PathManager pathManager = new PathManagerImpl();

    int x = 4;
    int y = 8;

    @Test
    @DisplayName("사는 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 1);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> guard.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사는 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 2);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> guard.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사는 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y + 1);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> guard.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사는 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        Position from = new Position(5, 8);
        Position to = new Position(5, 10);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> guard.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사는 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 1, y);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> guard.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사는 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 2, y);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> guard.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사는 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 1, y);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> guard.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사는 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 2, y);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> guard.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("사는 궁성 내에서 대각선으로 이동할 수 있다.")
    void test9() {
        // given
        Position from = new Position(5, 9);
        Position to = new Position(6, 10);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> guard.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("사는 궁성 외에서 대각선으로 이동할 수 없다.")
    void test10() {
        // given
        Position from = new Position(3, 3);
        Position to = new Position(4, 4);
        Guard guard = new Guard(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> guard.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
