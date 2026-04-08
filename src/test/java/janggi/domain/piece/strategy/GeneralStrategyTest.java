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

class GeneralStrategyTest {

    private static final int DISTANCE = 1;

    private final MoveStrategy moveStrategy = new GeneralStrategy();

    private static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(CampType.CHO, new Position(0, 3), new Position(0, 4)),
                Arguments.of(CampType.CHO, new Position(2, 5), new Position(2, 4)),

                Arguments.of(CampType.CHO, new Position(1, 4), new Position(1, 5)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(1, 3)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(0, 4)),
                Arguments.of(CampType.CHO, new Position(1, 4), new Position(2, 4)),

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
    @MethodSource("successMovePositions")
    void 직선_방향으로_1칸만_이동한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.GENERAL, campType)));
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    private static Stream<Arguments> invalidDistancePositions() {
        return Stream.of(
                Arguments.of(CampType.CHO, new Position(0, 3), new Position(2, 5)),
                Arguments.of(CampType.CHO, new Position(0, 3), new Position(0, 5)),
                Arguments.of(CampType.HAN, new Position(9, 3), new Position(7, 5)),
                Arguments.of(CampType.HAN, new Position(9, 4), new Position(7, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDistancePositions")
    void 직선_방향으로_1칸만_이동하지_않으면_예외가_발생한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.GENERAL, campType)));
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
    }

    private static Stream<Arguments> invalidRangePositions() {
        return Stream.of(
                Arguments.of(CampType.CHO, new Position(0, 2), new Position(0, 3)),
                Arguments.of(CampType.CHO, new Position(0, 6), new Position(0, 5)),
                Arguments.of(CampType.CHO, new Position(3, 3), new Position(2, 4)),
                Arguments.of(CampType.HAN, new Position(8, 2), new Position(8, 3)),
                Arguments.of(CampType.HAN, new Position(7, 5), new Position(7, 6)),
                Arguments.of(CampType.HAN, new Position(6, 2), new Position(7, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRangePositions")
    void 궁성_내에서_이동하지_않으면_예외가_발생한다(CampType campType, Position source, Position destination) {
        Board board = new Board(Map.of(source, new Piece(PieceRule.GENERAL, campType)));
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
    }
}
