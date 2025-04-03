package piece;

import static org.assertj.core.api.Assertions.*;

import game.Team;
import location.PathManager;
import location.PathManagerImpl;
import location.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    PathManager pathManager = new PathManagerImpl();

    @Test
    @DisplayName("수평 방향으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(2, 3);
        Position to = new Position(3, 3);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> cannon.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수직 방향으로 이동할 수 있다.")
    void test2() {
        //given
        Position from = new Position(2, 3);
        Position to = new Position(2, 10);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> cannon.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(6, 3);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);

        //when
        //then
        assertThatThrownBy(() -> cannon.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물 하나를 넘으면 이동할 수 있다.")
    void test11() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 8);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Guard(2, Team.GREEN, pathManager, new Position(2, 4))));

        //when
        //then
        assertThatCode(() -> cannon.validatePaths(pieces, to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기물을 두 개 이상 넘을 수 없다.")
    void test12() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 8);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Guard(2, Team.GREEN, pathManager, new Position(2, 4)),
                new Guard(3, Team.GREEN, pathManager, new Position(2, 5))));

        //when
        //then
        assertThatThrownBy(() -> cannon.validatePaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포를 뛰어넘을 수 없다.")
    void test13() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 8);
        Cannon cannon = new Cannon(1, Team.GREEN, pathManager, from);
        Pieces pieces = new Pieces(List.of(
                cannon,
                new Cannon(2, Team.GREEN, pathManager, new Position(2, 4))));

        //when
        //then
        assertThatThrownBy(() -> cannon.validatePaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
