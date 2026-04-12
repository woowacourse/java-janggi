package domain.piece;

import domain.board.Board;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HorseTest {

    @Test
    void 위로_오른대각선_이동_가능() {
        Board board = new Board();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(3, 7);

        Assertions.assertTrue(horse.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 위로_오른대각선_이동() {
        Board board = new Board();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(3, 7);

        board.locatePiece(fromPosition, horse);
        board.move(fromPosition, toPosition);

        Assertions.assertFalse(board.isExist(fromPosition));
        Assertions.assertEquals(board.getPieceFrom(toPosition), horse);
    }

    @Test
    void 위로_오른대각선_경로_막힘() {
        Board board = new Board();
        Horse horse = new Horse(Camp.HAN);
        Piece piece = new Soldier(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(3, 7);

        board.locatePiece(fromPosition, horse);
        board.locatePiece(new Position(2, 8), piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                IllegalArgumentException.class).hasMessageContaining("[ERROR]", "이동할");
    }

    @Test
    void 이동_불가_좌표_이동_불가() {
        Board board = new Board();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(0, 0);

        Assertions.assertFalse(horse.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 이동_불가_좌표_이동_불가2() {
        Board board = new Board();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 0);
        Position toPosition = new Position(3, 0);

        Assertions.assertFalse(horse.canMove(fromPosition, toPosition, board));
    }
}
