package domain.piece;

import domain.board.Board;
import domain.position.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CannonTest {

    @Test
    void 다른_기물을_넘고_이동_가능() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);
        Soldier piece = new Soldier(Camp.HAN);

        Position fromPosition = new Position(3, 5);
        Position anotherPiecePosition = new Position(5, 5);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(anotherPiecePosition, piece);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(3, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(4, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(5, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(6, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(7, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(8, 5), board));
    }

    @Test
    void 포에_막혀_이동_불가() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);
        Cannon cannonForBlock = new Cannon(Camp.HAN);

        Position fromPosition = new Position(3, 5);
        Position anotherPiecePosition = new Position(5, 5);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(anotherPiecePosition, cannonForBlock);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(3, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(4, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(5, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(6, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(7, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(8, 5), board));
    }

    @Test
    void 기물을_넘고_다른_기물을_만나기_전까지_이동_가능() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);
        Soldier pieceA = new Soldier(Camp.HAN);
        Soldier pieceB = new Soldier(Camp.HAN);

        Position fromPosition = new Position(3, 5);
        Position anotherPieceAPosition = new Position(5, 5);
        Position anotherPieceBPosition = new Position(7, 5);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(anotherPieceAPosition, pieceA);
        board.locatePiece(anotherPieceBPosition, pieceB);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(3, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(4, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(5, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(6, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(7, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(8, 5), board));
    }

    @Test
    void 기물을_넘고_다른_기물을_만나기_전까지_이동_가능_포는_못잡음() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);
        Soldier pieceA = new Soldier(Camp.CHO);
        Cannon pieceB = new Cannon(Camp.CHO);

        Position fromPosition = new Position(3, 5);
        Position anotherPieceAPosition = new Position(5, 5);
        Position anotherPieceBPosition = new Position(7, 5);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(anotherPieceAPosition, pieceA);
        board.locatePiece(anotherPieceBPosition, pieceB);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(3, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(4, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(5, 5), board));
        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(6, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(7, 5), board));
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(8, 5), board));
    }

    @Test
    void 궁성_안에서_기물을_넘고_대각선_이동() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);
        Soldier pieceA = new Soldier(Camp.CHO);

        Position fromPosition = new Position(3, 9);
        Position pieceAPosition = new Position(4, 8);
        Position toPosition = new Position(5, 7);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(pieceAPosition, pieceA);

        Assertions.assertFalse(cannon.canMove(fromPosition, pieceAPosition, board));
        Assertions.assertTrue(cannon.canMove(fromPosition, toPosition, board));

        //뛰어넘고 궁성밖 대각선은 안됨
        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(6,6), board));
    }

    @Test
    void 궁성_안에서_기물을_넘지않으면_대각선이동_실패() {
        Board board = new Board();
        Cannon cannon = new Cannon(Camp.HAN);

        Position fromPosition = new Position(3, 9);
        Position toPosition = new Position(5, 7);

        board.locatePiece(fromPosition, cannon);

        Assertions.assertFalse(cannon.canMove(fromPosition, toPosition, board));
    }
}
