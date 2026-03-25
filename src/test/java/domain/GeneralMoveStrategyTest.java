package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralMoveStrategyTest {

    @Test
    @DisplayName("장은 상하좌우로 한 칸 이동할 수 있다.")
    void general_move_test() {
        Position position = Position.of(1, 4);
        Position targetPosition  = Position.of(1,5);
        GeneralMoveStrategy generalMoveStrategy = GeneralMoveStrategy.of(position);

        boolean isMoveable = generalMoveStrategy.isMoveAble(targetPosition);

        Assertions.assertThat(isMoveable).isTrue();
    }

}