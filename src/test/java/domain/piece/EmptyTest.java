package domain.piece;

import static domain.Fixtures._EIGHT_ONE;
import static domain.Fixtures._NINE_ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyTest {

    @Test
    void 기물이_없다는_것을_객체로_표현할_수_있다() {
        // when & then
        Assertions.assertDoesNotThrow(() -> new Empty());
    }

    @Test
    void 기물이_존재하지_않을_때_움직이려_하는_경우_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> new Empty().findMovablePath(_NINE_ONE, _EIGHT_ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
