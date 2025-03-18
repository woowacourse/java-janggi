package domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 좌표를_생성한다() {
        assertThatCode(() -> new Position(1, 2))
                .doesNotThrowAnyException();
    }
}
