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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CannonStrategyTest {

    private static final long REQUIRED_PIECE_COUNT = 1;

    private final MoveStrategy moveStrategy = new CannonStrategy();

    private static Stream<Arguments> successMovePositions() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),
                Arguments.of(new Position(2, 3), new Position(0, 5)),
                Arguments.of(new Position(0, 3), new Position(2, 5)),
                Arguments.of(new Position(6, 4), new Position(0, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("successMovePositions")
    void 직선_방향으로_하나의_기물을_넘어_이동한다(Position source, Position destination) {
        //given
        Map<Position, Piece> pieceMap = new HashMap<>();
        pieceMap.put(source, new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(0, 3), new Piece(PieceRule.SOLDIER, CampType.CHO));
        pieceMap.put(new Position(1, 4), new Piece(PieceRule.GENERAL, CampType.CHO));
        BoardChecker board = new Board(pieceMap);

        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, board));
    }

    @Test
    void 목적지까지_직선_방향으로_이동하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(9, 3);
        Position destination = new Position(6, 6);
        BoardChecker board = new Board(Map.of(
                source, new Piece(PieceRule.CANNON, CampType.HAN),
                new Position(8, 4), new Piece(PieceRule.GENERAL, CampType.HAN)
        ));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private static Stream<Arguments> noPieceInPathCases() {
        return Stream.of(
                Arguments.of(new Position(0, 5), new Position(0, 0)),
                Arguments.of(new Position(2, 3), new Position(0, 5)),
                Arguments.of(new Position(9, 5), new Position(7, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("noPieceInPathCases")
    void 이동하려는_경로에_기물이_존재하지_않으면_예외가_발생한다(Position source, Position destination) {
        // given
        BoardChecker board = new Board(Map.of(
                source, new Piece(PieceRule.CANNON, CampType.CHO),
                new Position(2, 4), new Piece(PieceRule.GENERAL, CampType.CHO)
        ));
        // when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT));
    }

    private static Stream<Arguments> multiplePiecesInPathCases() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),
                Arguments.of(new Position(6, 3), new Position(9, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("multiplePiecesInPathCases")
    void 이동하려는_경로에_기물이_2개_이상_존재하면_예외가_발생한다(Position source, Position destination) {
        //given
        Map<Position, Piece> pieceMap = new HashMap<>();
        pieceMap.put(source, new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(0, 3), new Piece(PieceRule.CHARIOT, CampType.CHO));
        pieceMap.put(new Position(0, 4), new Piece(PieceRule.ELEPHANT, CampType.CHO));
        pieceMap.put(new Position(7, 3), new Piece(PieceRule.CHARIOT, CampType.HAN));
        pieceMap.put(new Position(8, 3), new Piece(PieceRule.ELEPHANT, CampType.HAN));
        BoardChecker board = new Board(pieceMap);

        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(1));
    }

    private static Stream<Arguments> samePieceTypeInPathCases() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),
                Arguments.of(new Position(7, 3), new Position(9, 5))
        );
    }

    @ParameterizedTest
    @MethodSource("samePieceTypeInPathCases")
    void 이동하려는_경로에_있는_기물이_같은_타입이면_예외가_발생한다(Position source, Position destination) {
        //given
        Map<Position, Piece> pieceMap = new HashMap<>();
        pieceMap.put(source, new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(0, 4), new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(8, 4), new Piece(PieceRule.CANNON, CampType.HAN));
        BoardChecker board = new Board(pieceMap);

        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
    }

    private static Stream<Arguments> samePieceTypeAtDestinationCases() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),
                Arguments.of(new Position(0, 4), new Position(2, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("samePieceTypeAtDestinationCases")
    void 목적지에_있는_기물이_같은_타입이면_예외가_발생한다(Position source, Position destination) {
        //given
        Map<Position, Piece> pieceMap = new HashMap<>();
        pieceMap.put(source, new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(0, 3), new Piece(PieceRule.SOLDIER, CampType.CHO));
        pieceMap.put(new Position(0, 5), new Piece(PieceRule.CANNON, CampType.CHO));
        pieceMap.put(new Position(1, 4), new Piece(PieceRule.SOLDIER, CampType.CHO));
        pieceMap.put(new Position(2, 4), new Piece(PieceRule.CANNON, CampType.HAN));
        BoardChecker board = new Board(pieceMap);

        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
    }

    //궁성 밖에서는 대각선 이동이 불가능 9,3-> 6,6
}
