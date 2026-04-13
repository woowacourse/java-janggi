package domain.piece;

import domain.board.Board;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralTest {

    @Test
    void 아래로_오른대각선_이동_가능() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(4, 1);
        Position toPosition = new Position(5, 2);

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
        Soldier piece = new Soldier(Camp.HAN);
        Position fromPosition = new Position(4, 1);
        Position toPosition = new Position(5, 2);

        board.locatePiece(fromPosition, general);
        board.locatePiece(toPosition, piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                IllegalArgumentException.class).hasMessageContaining("[ERROR]", "이동할");
    }

    @Test
    void 이동_불가_좌표_이동_불가() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(0, 0);

        Assertions.assertFalse(general.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 왕은_궁성_밖으로_이동불가() {
        Board board = new Board();
        General general = new General(Camp.HAN);
        Position fromPosition = new Position(5, 8);
        Position toPosition = new Position(6, 8);

        Assertions.assertFalse(general.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 궁성_연결안된_대각선_이동불가() {
        Board board = new Board();
        General general = new General(Camp.CHO);
        Position fromPosition = new Position(4,9);
        Position toPosition = new Position(3,8);

        Assertions.assertFalse(general.canMove(fromPosition, toPosition, board));
    }
}
