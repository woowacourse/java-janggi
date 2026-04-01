package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class SlidingStrategyTest {

    private final MoveStrategy strategy = new SlidingStrategy();

    @Test
    void 직선으로_여러_칸_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(9, 0);
        //when
        List<Position> path = strategy.findPath(source, destination, Camp.CHO);
        //then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(path).hasSize(9);
            assertSoftly.assertThat(path.getLast()).isEqualTo(destination);
        });
    }

    @Test
    void 직선으로_이동하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(3, 3);
        //when & then
        assertThatThrownBy(() -> strategy.findPath(source, destination, Camp.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    @Test
    void 제자리에_있으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 0);
        //when & then
        assertThatThrownBy(() -> strategy.findPath(source, destination, Camp.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PIECE_MUST_MOVE.getMessage());
    }
}
