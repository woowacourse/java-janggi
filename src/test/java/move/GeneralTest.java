package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.General;

public class GeneralTest {

    String GREEN_GENERAL_EXPRESSION = "g";

    @Test
    @DisplayName("궁성 내 기물은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 1);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 2);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        int x = 0;
        int y = 1;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 1);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 2);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 1, y);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 2, y);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 1, y);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 2, y);
        General general = new General(GREEN_GENERAL_EXPRESSION, from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
