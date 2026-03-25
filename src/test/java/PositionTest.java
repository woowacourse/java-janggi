import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {

    @Test
    @DisplayName("x 좌표가 0 미만일 경우 예외가 발생한다.")
    void throwException_When_XCoordinateLessThanZero() {
        assertThatThrownBy(() -> new Position(-1, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("x 좌표가 9 초과인 경우 예외가 발생한다.")
    void throwException_When_XCoordinateMoreThanNine() {
        assertThatThrownBy(() -> new Position(10, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
