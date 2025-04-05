package janggi.domain.piece.direction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MovementsTest {

    @Test
    void 적절한_움직임이_아닐_경우_예외가_발생한다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Position arrivalPosition = new Position(4, 3);
        final Movements movements = new Movements(List.of(new Movement(Direction.RIGHT)));
        final boolean canMoveIterable = false;

        // When & Then
        assertThatThrownBy(
                () -> movements.findMovements(currentPosition, arrivalPosition, canMoveIterable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
