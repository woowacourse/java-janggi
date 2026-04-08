package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierStrategyTest {

    private static final int DISTANCE = 1;

    private final MoveStrategy moveStrategy = new SoldierStrategy();

    private static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(CampType.HAN, new Position(6, 0), new Position(5, 0)),
                Arguments.of(CampType.HAN, new Position(6, 0), new Position(6, 1)),
                Arguments.of(CampType.HAN, new Position(6, 1), new Position(6, 0)),
                Arguments.of(CampType.HAN, new Position(7, 5), new Position(6, 5)),
                Arguments.of(CampType.CHO, new Position(0, 3), new Position(0, 2)),
                Arguments.of(CampType.CHO, new Position(3, 0), new Position(3, 1)),
                Arguments.of(CampType.CHO, new Position(3, 0), new Position(3, 1)),
                Arguments.of(CampType.CHO, new Position(3, 1), new Position(3, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositions")
    void 전진_또는_좌우_방향으로_1칸만_이동한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, campType)));
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    private static Stream<Arguments> successMovePositionsInPalace() {
        return Stream.of(
                Arguments.of(CampType.CHO, new Position(0, 3), new Position(1, 4)),
                Arguments.of(CampType.CHO, new Position(0, 5), new Position(1, 4)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(2, 3)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(2, 5)),
                Arguments.of(CampType.HAN, new Position(9, 3), new Position(8, 4)),
                Arguments.of(CampType.HAN, new Position(9, 5), new Position(8, 4)),
                Arguments.of(CampType.HAN, new Position(8, 4), new Position(7, 3)),
                Arguments.of(CampType.HAN, new Position(8, 4), new Position(7, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositionsInPalace")
    void 궁성에서_전진_또는_좌우_방향으로_1칸만_이동한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, campType)));
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    private static Stream<Arguments> invalidDistancePositions() {
        return Stream.of(
                Arguments.of(CampType.HAN, new Position(6, 0), new Position(4, 0)),
                Arguments.of(CampType.CHO, new Position(3, 0), new Position(3, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDistancePositions")
    void 직선_방향으로_1칸만_이동하지_않으면_예외가_발생한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, campType)));
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
    }

    private static Stream<Arguments> backwardMovePositions() {
        return Stream.of(
                Arguments.of(CampType.HAN, new Position(6, 0), new Position(7, 0)),
                Arguments.of(CampType.CHO, new Position(3, 0), new Position(2, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("backwardMovePositions")
    void 후진하는_경우_예외가_발생한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, campType)));
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
    }

    private static Stream<Arguments> backwardMovePositionsInPalace() {
        return Stream.of(
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(0, 3)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(0, 5)),
                Arguments.of(CampType.CHO, new Position(2, 3), new Position(1, 4)),
                Arguments.of(CampType.CHO, new Position(2, 5), new Position(1, 4)),
                Arguments.of(CampType.HAN, new Position(8, 4), new Position(9, 3)),
                Arguments.of(CampType.HAN, new Position(8, 4), new Position(9, 5)),
                Arguments.of(CampType.HAN, new Position(7, 3), new Position(8, 4)),
                Arguments.of(CampType.HAN, new Position(7, 5), new Position(8, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("backwardMovePositionsInPalace")
    void 궁성에서_후진하는_경우_예외가_발생한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, campType)));
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
    }
}
