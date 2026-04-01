package janggi.model.board.movement;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.model.movement.Movement;
import janggi.model.movement.OneStepMovement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OneStepMovementTest {

    @DisplayName("한 칸 떨어져 있지 않으면 예외가 발생한다.")
    @Test
    void OneStepMovement() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Movement movement = new OneStepMovement();

        //when & then
        assertThatThrownBy(() ->
                movement.move(
                        from,
                        new Position(Row.NINE, Column.EIGHT)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 지점이 한 칸 떨어져 있지 않습니다.");

        assertThatThrownBy(() ->
                movement.move(
                        from,
                        new Position(Row.FIVE, Column.FIVE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 지점이 한 칸 떨어져 있지 않습니다.");

        assertThatThrownBy(() ->
                movement.move(
                        from,
                        new Position(Row.SEVEN, Column.SIX)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 지점이 한 칸 떨어져 있지 않습니다.");
    }
}