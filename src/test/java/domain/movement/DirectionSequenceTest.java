package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movement.exception.InvalidDirectionSequenceException;
import domain.movement.exception.MovementErrorMessage;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DirectionSequenceTest {

    @Test
    void 방향_조합을_적용한_전체_위치를_순서대로_반환한다() {
        // given
        Position departure = new Position(3, 3);
        DirectionSequence sequence = DirectionSequence.of(
                Direction.UP,
                Direction.RIGHT_UP,
                Direction.RIGHT_UP
        );

        // when
        DirectionSequenceResult result = sequence.positionsFrom(departure);

        // then
        assertThat(result.pathPositions()).containsExactly(
                departure.moveUp(),
                departure.moveUp().moveRightUp()
        );
        assertThat(result.lastPosition()).isEqualTo(
                departure.moveUp().moveRightUp().moveRightUp()
        );
    }

    @Test
    void 방향_조합이_null이면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> new DirectionSequence(null))
                .isInstanceOf(InvalidDirectionSequenceException.class)
                .hasMessage(MovementErrorMessage.NULL_DIRECTIONS.message());
    }

    @Test
    void 방향_조합이_비어있으면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> new DirectionSequence(List.of()))
                .isInstanceOf(InvalidDirectionSequenceException.class)
                .hasMessage(MovementErrorMessage.EMPTY_DIRECTIONS.message());
    }

    @Test
    void 방향_조합에_null_방향이_포함되면_예외가_발생한다() {
        // given
        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.UP);
        directions.add(null);

        // when & then
        assertThatThrownBy(() -> new DirectionSequence(directions))
                .isInstanceOf(InvalidDirectionSequenceException.class)
                .hasMessage(MovementErrorMessage.NULL_DIRECTION_IN_SEQUENCE.message());
    }

}
