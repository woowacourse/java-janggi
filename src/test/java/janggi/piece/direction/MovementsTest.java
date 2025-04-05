package janggi.piece.direction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.move.Direction;
import janggi.move.Movement;
import janggi.move.Movements;
import janggi.move.Piece;
import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MovementsTest {

    @Test
    void 적절한_움직임이_아닐_경우_예외가_발생한다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Position arrivalPosition = new Position(4, 3);
        final Movements movements = new Movements(List.of(new Movement(Direction.RIGHT)));

        // When & Then
        assertThatThrownBy(
                () -> movements.findMovements(currentPosition, arrivalPosition, Piece.HAN_SOLDIER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
