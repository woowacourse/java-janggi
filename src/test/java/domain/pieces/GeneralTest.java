package domain.pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 아래로_오른대각선_이동_가능() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(4, 1);

        Assertions.assertTrue(general.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 아래로_오른대각선_이동() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(4, 1);
        Position toPosition = new Position(5, 2);

        board.locatePiece(fromPosition, general);
        board.move(fromPosition, toPosition);

        Assertions.assertFalse(board.isExist(fromPosition));
        Assertions.assertEquals(board.getPieceFrom(toPosition), general);
    }

    @Test
    void 아래로_오른대각선_경로_막힘() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        General piece = new General(Camp.HAN);
        Position fromPosition = new Position(4, 1);
        Position toPosition = new Position(5, 2);

        board.locatePiece(fromPosition, general);
        board.locatePiece(toPosition, piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                InvalidMoveException.class).hasMessageContaining("[ERROR]", "이동할");
    }

    @Test
    void 궁성_밖으로_이동_불가() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(5, 0);
        Position toPosition = new Position(6, 0);

        board.locatePiece(fromPosition, general);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                InvalidMoveException.class).hasMessageContaining("[ERROR]", "이동할");
    }
}
