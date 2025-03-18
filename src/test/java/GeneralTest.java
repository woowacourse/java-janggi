import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @Test
    @DisplayName("장군은 위로 한 칸 이동할 수 있다.")
    void test() {
        // given
        int x = 0;
        int y = 0;
        General general = new General(x, y);

        Position expectedPosition = new Position(x, y+1);

        // when
        general.move(x, y+1);

        // then
        Assertions.assertThat(general.position())
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
        Assertions.assertThatThrownBy(() -> general.move(x, y+2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
