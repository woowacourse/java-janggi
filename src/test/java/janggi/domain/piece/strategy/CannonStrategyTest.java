package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CannonStrategyTest {

    private static final long REQUIRED_PIECE_COUNT = 1;

    private final MoveStrategy moveStrategy = new CannonStrategy();

    @Test
    void 직선_방향으로_하나의_기물을_넘어_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceStrategy.SOLDIER, Camp.CHO)
        ));
        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON));
    }

    @Test
    void 목적지까지_직선_방향으로_이동하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(1, 1);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    @Test
    void 이동하려는_경로에_기물이_존재하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT));
    }

    @Test
    void 이동하려는_경로에_기물이_2개_이상_존재하면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceStrategy.CHARIOT, Camp.CHO),
                new Position(0, 4), new Piece(PieceStrategy.ELEPHANT, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(1));
    }

    @Test
    void 이동하려는_경로에_있는_기물이_같은_타입이면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 4), new Piece(PieceStrategy.CANNON, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
    }

    @Test
    void 목적지에_있는_기물이_같은_타입이면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceStrategy.SOLDIER, Camp.CHO),
                new Position(0, 5), new Piece(PieceStrategy.CANNON, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
    }
}
