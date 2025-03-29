package move;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import direction.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.GreenSoldier;
import piece.Soldier;

class SoldierTest {

    String GREEN_SOLDIER_EXPRESSION = "s";

    @Test
    @DisplayName("졸은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 1);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 2);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 아래로 한 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 1);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 1, y);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 2, y);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 1, y);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> soldier.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("졸은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 2, y);
        Soldier soldier = new GreenSoldier(GREEN_SOLDIER_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> soldier.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
