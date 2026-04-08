package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseStrategyTest {

    private static final int STRAIGHT_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 1;

    private final MoveStrategy moveStrategy = new HorseStrategy();

    private static Stream<Arguments> successPaths() {
        Position source = new Position(6, 4);
        return Stream.of(
                Arguments.of(source, new Position(8, 5)),
                Arguments.of(source, new Position(8, 3)),
                Arguments.of(source, new Position(4, 3)),
                Arguments.of(source, new Position(4, 5)),
                Arguments.of(source, new Position(5, 2)),
                Arguments.of(source, new Position(7, 2)),
                Arguments.of(source, new Position(5, 6)),
                Arguments.of(source, new Position(7, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("successPaths")
    void 직선_1칸_이동_후_대각선_1칸_이동한다(Position source, Position destination) {
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, new Board(Map.of())));
    }

    private static Stream<Arguments> invalidDistancePositions() {
        return Stream.of(
                Arguments.of(new Position(6, 4), new Position(5, 1)),
                Arguments.of(new Position(6, 4), new Position(4, 2)),
                Arguments.of(new Position(6, 4), new Position(6, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDistancePositions")
    void 행마법_대로_움직이지_않으면_예외가_발생한다(Position source, Position destination) {
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, new Board(Map.of())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, DIAGONAL_DISTANCE));
    }

    @Test
    void 이동하려는_경로에_기물이_존재하면_예외가_발생한다() {
        BoardChecker board = new Board(Map.of(
                new Position(8, 2), new Piece(PieceRule.SOLDIER, CampType.CHO)
        ));
        Position source = new Position(9, 2);
        Position destination = new Position(7, 3);

        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
    }
}
