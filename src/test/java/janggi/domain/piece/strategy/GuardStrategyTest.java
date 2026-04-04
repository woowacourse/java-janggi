package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardStrategyTest {

    private static final int DISTANCE = 1;

    private final MoveStrategy moveStrategy = new GuardStrategy();

    private static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(new Position(8, 3), new Position(7, 3)),
                Arguments.of(new Position(8, 3), new Position(9, 3)),
                Arguments.of(new Position(8, 3), new Position(8, 2)),
                Arguments.of(new Position(8, 3), new Position(8, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositions")
    void 직선_방향으로_1칸만_이동한다(Position source, Position destination) {
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, Camp.HAN, null, PieceStrategy.GUARD));
    }

    @Test
    void 직선_방향으로_1칸만_이동하지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> moveStrategy.validate(new Position(3, 0), new Position(5, 0), Camp.HAN, null, PieceStrategy.GUARD))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
    }
}
