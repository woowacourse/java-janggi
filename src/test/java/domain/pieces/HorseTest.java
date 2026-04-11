package domain.pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HorseTest {

    @Test
    void 위로_오른대각선_이동_가능() {
        Board board = Board.empty();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(3, 7);

        Assertions.assertTrue(horse.canMove(fromPosition, toPosition, board));
    }

    @Test
    void 위로_오른대각선_이동() {
        Board board = Board.empty();
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
        Board board = Board.empty();
        Horse horse = new Horse(Camp.HAN);
        Horse piece = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(3, 7);

        board.locatePiece(fromPosition, horse);
        board.locatePiece(new Position(2, 8), piece);

        assertThatThrownBy(() -> board.move(fromPosition, toPosition)).isInstanceOf(
                InvalidMoveException.class).hasMessageContaining("[ERROR]", "이동할");
    }

    @Test
    void 이동_불가_좌표_이동_불가() {
        Board board = Board.empty();
        Horse horse = new Horse(Camp.HAN);
        Position fromPosition = new Position(2, 9);
        Position toPosition = new Position(0, 0);

        Assertions.assertFalse(horse.canMove(fromPosition, toPosition, board));
    }
}
