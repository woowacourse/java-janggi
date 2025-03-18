import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class JanggiTest {
    @DisplayName("x좌표는 0~8의 범위가 아니면 에러를 던진다.")
    @Test
    void x_range_test() {
        // given
        int x  = 9;

        // when, then
        assertThatCode(() -> new LocationX(x)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("y좌표는 0~9의 범위가 아니면 예외를 던진다.")
    @Test
    void y_range_test() {
        // given
        int y = 10;

        // when, then
        assertThatCode(() -> new LocationY(y)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
