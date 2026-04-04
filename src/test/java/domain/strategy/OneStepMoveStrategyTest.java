package domain.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OneStepMoveStrategyTest {
    @Test
    @DisplayName("방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void OneStepMoveDirectionSizeExceptionTest() {
        MoveStrategy moveStrategy = new OneStepMoveStrategy();

        Position from = new Position(3, 0);
        Position to = new Position(3, 2);

        assertThatThrownBy(() -> moveStrategy.path(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 한 칸만 이동할 수 있습니다.");
    }
}
