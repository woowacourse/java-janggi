package domain.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movement.exception.EmptyDirectionSequenceResultException;
import domain.movement.exception.MovementErrorMessage;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class DirectionSequenceResultTest {

    @Test
    void 마지막_위치를_반환한다() {
        // given
        List<Position> positions = List.of(
                new Position(3, 3),
                new Position(4, 3),
                new Position(5, 3)
        );
        DirectionSequenceResult result = new DirectionSequenceResult(positions);

        // when
        Position lastPosition = result.lastPosition();

        // then
        assertThat(lastPosition).isEqualTo(new Position(5, 3));
    }

    @Test
    void 마지막_위치를_제외한_경로를_반환한다() {
        // given
        List<Position> positions = List.of(
                new Position(3, 3),
                new Position(4, 3),
                new Position(5, 3)
        );
        DirectionSequenceResult result = new DirectionSequenceResult(positions);

        // when
        List<Position> pathPositions = result.pathPositions();

        // then
        assertThat(pathPositions).containsExactly(
                new Position(3, 3),
                new Position(4, 3)
        );
    }

    @Test
    void 이동_경로가_한_칸이면_경로_목록은_비어_있다() {
        // given
        DirectionSequenceResult result = new DirectionSequenceResult(
                List.of(new Position(3, 3))
        );

        // when
        List<Position> pathPositions = result.pathPositions();

        // then
        assertThat(pathPositions).isEmpty();
    }

    @Test
    void 이동_경로가_비어있으면_마지막_위치_조회_시_예외를_던진다() {
        // given
        DirectionSequenceResult result = new DirectionSequenceResult(List.of());

        // when & then
        assertThatThrownBy(result::lastPosition)
                .isInstanceOf(EmptyDirectionSequenceResultException.class)
                .hasMessage(MovementErrorMessage.EMPTY_DIRECTION_SEQUENCE_RESULT.message());
    }
}
