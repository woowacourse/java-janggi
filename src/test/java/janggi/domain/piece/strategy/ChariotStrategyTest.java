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
                moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CHARIOT));
    }

    @Test
    void 가로_방향_직선으로_여러_칸_이동한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 8);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatNoException().isThrownBy(() ->
                moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CHARIOT));
    }

    @Test
    void 직선으로_이동하지_않으면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(3, 3);
        BoardChecker board = new Board(Map.of());
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    @Test
    void 이동하려는_경로에_기물이_존재하면_예외가_발생한다() {
        //given
        Position source = new Position(0, 0);
        Position destination = new Position(5, 0);
        Position blockingPosition = new Position(3, 0);
        BoardChecker board = new Board(Map.of(blockingPosition, new Piece(PieceStrategy.SOLDIER, Camp.CHO)));
        //when & then
        assertThatThrownBy(() -> moveStrategy.validate(source, destination, Camp.CHO, board, PieceStrategy.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
    }
}
