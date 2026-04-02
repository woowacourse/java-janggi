package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JumpingSlidingRuleTest {

    private static final int REQUIRED_PIECE_COUNT = 1;

    private final MoveRule rule = new JumpingSlidingRule();

    @Test
    void 직선_방향으로_하나의_기물을_넘어_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceRule.SOLDIER, Camp.CHO)
        ));
        //when & then
        assertThatNoException().isThrownBy(() -> rule.validate(source, destination, Camp.CHO, board, PieceRule.CANNON));
    }

    @Test
    void 이동하려는_경로에_기물이_존재하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatThrownBy(() -> rule.validate(source, destination, Camp.CHO, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT));
    }

    @Test
    void 이동하려는_경로에_기물이_2개_이상_존재하면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceRule.CHARIOT, Camp.CHO),
                new Position(0, 4), new Piece(PieceRule.ELEPHANT, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> rule.validate(source, destination, Camp.CHO, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT));
    }

    @Test
    void 이동하려는_경로에_있는_기물이_같은_타입이면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 4), new Piece(PieceRule.CANNON, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> rule.validate(source, destination, Camp.CHO, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
    }

    @Test
    void 목적지에_있는_기물이_같은_타입이면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 5);
        BoardChecker board = new Board(Map.of(
                new Position(0, 3), new Piece(PieceRule.SOLDIER, Camp.CHO),
                new Position(0, 5), new Piece(PieceRule.CANNON, Camp.CHO)
        ));
        //when & then
        assertThatThrownBy(() -> rule.validate(source, destination, Camp.CHO, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
    }
}
