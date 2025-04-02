package piece;

import static org.assertj.core.api.Assertions.*;

import game.Team;
import location.PathManager;
import location.PathManagerImpl;
import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    PathManager pathManager = new PathManagerImpl();

    int x = 5;
    int y = 9;

    @Test
    @DisplayName("장군은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 1);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 2);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y + 1);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        Position from = new Position(5, 8);
        Position to = new Position(5, 10);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 1, y);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 2, y);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 1, y);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 2, y);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("초 장군은 궁성 밖으로 이동할 수 없다")
    void test9() {
        // given
        Position from = new Position(4, 1);
        Position notPalace = new Position(3, 1);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(notPalace))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("한 장군은 궁성 밖으로 이동할 수 없다")
    void test10() {
        // given
        Position from = new Position(4, 8);
        Position notPalace = new Position(3, 8);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(notPalace))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 대각선으로 1칸 이동할 수 있다")
    void test11() {
        // given
        Position from = new Position(5, 9);
        Position to = new Position(4, 8);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장군은 궁성이 아닌 대각선으로 이동할 수 없다")
    void test12() {
        //given
        Position from = new Position(4, 9);
        Position notPalaceDiagonal = new Position(5, 8);
        General general = new General(1, Team.GREEN, pathManager, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(notPalaceDiagonal))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
