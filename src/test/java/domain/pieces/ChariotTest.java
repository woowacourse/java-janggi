package domain.pieces;

import domain.Board;
import domain.Camp;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @Test
    void 다른_기물_뒤로는_이동할_수_없다() {
        Board board = new Board();
        Chariot chariot = new Chariot(Camp.HAN);
        Horse piece = new Horse(Camp.HAN);

        Position fromPosition = new Position(3, 5);
        Position anotherPiecePosition = new Position(6, 5);

        board.locatePiece(fromPosition, chariot);
        board.locatePiece(anotherPiecePosition, piece);

        Assertions.assertFalse(chariot.canMove(fromPosition, new Position(3, 5), board));
        Assertions.assertTrue(chariot.canMove(fromPosition, new Position(4, 5), board));
        Assertions.assertTrue(chariot.canMove(fromPosition, new Position(5, 5), board));
        Assertions.assertTrue(chariot.canMove(fromPosition, new Position(6, 5), board));
        Assertions.assertFalse(chariot.canMove(fromPosition, new Position(7, 5), board));
        Assertions.assertFalse(chariot.canMove(fromPosition, new Position(8, 5), board));
    }
}
