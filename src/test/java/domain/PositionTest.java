package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 범위_벗어난_좌표_예외_테스트() {
        assertThatThrownBy(() -> Position.create(11, 9))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 범위_안의_좌표_정상_테스트() {
        assertThatCode(() -> Position.create(10, 9))
                .doesNotThrowAnyException();
    }
}
