package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import game.Team;
import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    int x = 3;
    int y = 3;

    @Test
    @DisplayName("졸은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given

        Position from = new Position(x, y);
        Position to = new Position(x, y - 1);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 2);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 아래로 한 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y + 1);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 1, y);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 2, y);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 1, y);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 2, y);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 궁성 내에서 전진 대각선으로 1칸 이동할 수 있다.")
    void test9() {
        // given
        Position from = new Position(5, 2);
        Position to = new Position(4, 1);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 궁성 내에서 전진 대각선으로 2칸 이동할 수 없다.")
    void test10() {
        // given
        Position from = new Position(6, 3);
        Position to = new Position(4, 1);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 궁성 내에서 후진 대각선으로 1칸 이동할 수 없다.")
    void test11() {
        // given
        Position from = new Position(5, 2);
        Position to = new Position(4, 3);
        GreenSoldier soldier = new GreenSoldier(1, Team.GREEN, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
