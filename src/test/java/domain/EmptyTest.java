package domain;

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
        assertThatThrownBy(() -> new Empty().findPath(new Position(0, 0), new Position(1, 1)))
                .isInstanceOf(IllegalStateException.class);
    }
}
