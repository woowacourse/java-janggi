import static org.assertj.core.api.Assertions.*;

import move.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.General;

public class GeneralTest {

    @Test
    @DisplayName("장군은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        General general = new General(x, y);

        Position expectedPosition = new Position(x, y+1);

        // when
        general.move(new Position(x, y+1));

        // then
        assertThat(general.position())
                .isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("장군은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        General general = new General(x, y);

        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(x, y+2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        int x = 0;
        int y = 1;
        General general = new General(x, y);

        Position expectedPosition = new Position(x, y-1);

        // when
        general.move(new Position(x, y-1));

        // then
        assertThat(general.position())
                .isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("장군은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 2;
        General general = new General(x, y);

        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(x, y-2)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        General general = new General(x, y);

        Position expectedPosition = new Position(x-1, y);

        // when
        general.move(new Position(x-1, y));

        // then
        assertThat(general.position())
                .isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("장군은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 2;
        int y = 0;
        General general = new General(x, y);

        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(x-2, y)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("장군은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 1;
        int y = 0;
        General general = new General(x, y);

        Position expectedPosition = new Position(x+1, y);

        // when
        general.move(new Position(x+1, y));

        // then
        assertThat(general.position())
                .isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("장군은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 1;
        int y = 0;
        General general = new General(x, y);

        // when
        // then
        assertThatThrownBy(() -> general.move(new Position(x+2, y)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
