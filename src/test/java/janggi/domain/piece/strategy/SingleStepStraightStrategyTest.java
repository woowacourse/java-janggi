package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SingleStepStraightStrategyTest {

    private final MoveStrategy strategy = new SingleStepStraightStrategy();

    private static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(1, 5)),
                Arguments.of(new Position(1, 4), new Position(1, 3)),
                Arguments.of(new Position(1, 4), new Position(0, 4)),
                Arguments.of(new Position(1, 4), new Position(2, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositions")
    void 궁과_사는_상하좌우_1칸_이동한다(Position from, Position to) {
        //when
        List<Position> path = strategy.findPath(from, to, Camp.HAN);
        //then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(path).hasSize(1);
            assertSoftly.assertThat(path).containsExactly(to);
        });
    }

    @Test
    void 궁과_사는_1칸_이동이_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> strategy.findPath(new Position(3, 0), new Position(5, 0), Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
    }
}
