package move;

import static org.assertj.core.api.Assertions.*;

import location.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.General;

public class GeneralTest {

    int x = 3;
    int y = 3;

    @Test
    @DisplayName("궁성 내 기물은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 1);
        General general = new General(from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y - 2);
        General general = new General(from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y + 1);
        General general = new General(from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x, y + 2);
        General general = new General(from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 1, y);
        General general = new General(from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x - 2, y);
        General general = new General(from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 1, y);
        General general = new General(from);

        //when

        //then
        assertThatCode(() -> general.validateDestination(to))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        Position from = new Position(x, y);
        Position to = new Position(x + 2, y);
        General general = new General(from);

        //when

        //then
        assertThatThrownBy(() -> general.validateDestination(to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
