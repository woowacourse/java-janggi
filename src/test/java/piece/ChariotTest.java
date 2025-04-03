package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import game.Team;
import java.util.List;
import location.PathManager;
import location.PathManagerImpl;
import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {
    PathManager pathManager = new PathManagerImpl();

    @Test
    @DisplayName("수평 방향으로 이동할 수 있다.")
    void test1() {
        //given
        Position from = new Position(2, 3);
        Position to = new Position(3, 3);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> chariot.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수직 방향으로 이동할 수 있다.")
    void test2() {
        //given
        Position from = new Position(2, 3);
        Position to = new Position(2, 10);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> chariot.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(3, 3);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);

        //when
        //then
        assertThatThrownBy(() -> chariot.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 기물이 있을 시 이동할 수 없다.")
    void test10() {
        //given
        Position from = new Position(2, 2);
        Position to = new Position(2, 8);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);
        Pieces pieces = new Pieces(List.of(
                chariot,
                new Guard(2, Team.GREEN, pathManager, new Position(2, 4))));

        //when
        //then
        assertThatThrownBy(() -> chariot.validatePaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내에서는 대각선으로 이동할 수 있다.")
    void test11() {
        //given
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> chariot.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내에서는 대각선 경로에 기물이 있는 경우 이동할 수 없다.")
    void test12() {
        //given
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);
        Chariot chariot = new Chariot(1, Team.GREEN, pathManager, from);
        Pieces pieces = new Pieces(List.of(
                chariot,
                new Guard(1, Team.GREEN, pathManager, new Position(5, 2))));

        //when
        //then
        assertThatThrownBy(() -> chariot.validatePaths(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
