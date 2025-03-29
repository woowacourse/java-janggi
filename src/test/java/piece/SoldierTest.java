package piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

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
        GreenSoldier soldier = new GreenSoldier(from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
