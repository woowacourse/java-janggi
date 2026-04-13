package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotStrategyTest {

    private final MoveStrategy moveStrategy = new ChariotStrategy();

    @Test
    void 세로_방향_직선으로_여러_칸_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(9, 0);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    @Test
    void 가로_방향_직선으로_여러_칸_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 8);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    private static Stream<Arguments> successMovePositionsInPath() {
        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(2, 5)),
                Arguments.of(new Position(0, 4), new Position(1, 4)),
                Arguments.of(new Position(2, 5), new Position(0, 5)),
                Arguments.of(new Position(1, 5), new Position(1, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositionsInPath")
    void 궁성_내에서_직선으로_여러_칸_이동한다(Position source, Position destination) {
        //given
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    @Test
    void 직선으로_이동하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(3, 3);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private static Stream<Arguments> invalidDistancePositions() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(5, 0)),
                Arguments.of(new Position(0, 5), new Position(2, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDistancePositions")
    void 이동하려는_경로에_기물이_존재하면_예외가_발생한다(Position source, Position destination) {
        //given
        BoardChecker board = new Board(Map.of(
                new Position(3, 0), new Piece(PieceRule.CHARIOT, CampType.CHO),
                new Position(1, 4), new Piece(PieceRule.ELEPHANT, CampType.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
    }
}
