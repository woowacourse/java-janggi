package domain.piece;

import domain.board.Board;
import domain.Camp;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @Test
    void 다른_기물_뒤로는_이동할_수_없다() {
        Board board = new Board();
        Chariot chariot = new Chariot(Camp.HAN);
        Soldier piece = new Soldier(Camp.HAN);

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

    @Test
    void 차_궁성_대각선_이동() {
        Board board = new Board();
        Chariot chariot = new Chariot(Camp.CHO);
        Position fromPosition = new Position(3,9);

        Assertions.assertTrue(chariot.canMove(fromPosition, new Position(4,8), board));
        Assertions.assertTrue(chariot.canMove(fromPosition, new Position(5,7), board));

        //궁성 밖은 대각선 이동 불가
        Assertions.assertFalse(chariot.canMove(fromPosition, new Position(6,6), board));
    }

    @Test
    void 차_궁성_밖으로는_대각선_이동_불가() {
        Board board = new Board();
        Chariot chariot = new Chariot(Camp.CHO);
        Position fromPosition = new Position(3, 9);

        Assertions.assertFalse(chariot.canMove(fromPosition, new Position(6,6), board));
    }

    @Test
    void 궁성_연결안된_대각선_이동불가() {
        Board board = new Board();
        Chariot chariot = new Chariot(Camp.CHO);
        Position fromPosition = new Position(4, 9);
        Position toPosition = new Position(3,8);

        Assertions.assertFalse(chariot.canMove(fromPosition, toPosition, board));
    }
}
