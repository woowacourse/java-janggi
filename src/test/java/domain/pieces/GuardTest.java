package domain.pieces;

import domain.Board;
import domain.Camp;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuardTest {

    @Test
    void 아래로_오른대각선_이동_가능() {
        Board board = new Board();
        Guard guard = new Guard(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(4, 1);

        Assertions.assertTrue(guard.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 아래로_오른대각선_이동() {
        Board board = new Board();
        Guard guard = new Guard(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(4, 1);

        board.locatePiece(fromPosition, guard);
        board.move(fromPosition, toPosition);

        Assertions.assertFalse(board.isExist(fromPosition));
        Assertions.assertEquals(board.getPieceFrom(toPosition), guard);
    }

    @Test
    void 아래로_오른대각선_경로_막힘() {
        Board board = new Board();
        Guard guard = new Guard(Camp.HAN);
        Soldier piece = new Soldier(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(4, 1);

        board.locatePiece(fromPosition, guard);
        board.locatePiece(toPosition, piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                IllegalArgumentException.class).hasMessageContaining("[ERROR]", "이동할");
    }

    @Test
    void 이동_불가_좌표_이동_불가() {
        Board board = new Board();
        Guard guard = new Guard(Camp.HAN);
        Position fromPosition = new Position(3, 0);
        Position toPosition = new Position(0, 0);

        Assertions.assertFalse(guard.canMove(fromPosition, toPosition, board));
    }
}
