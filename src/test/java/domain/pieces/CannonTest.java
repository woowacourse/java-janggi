package domain.pieces;

import domain.Board;
import domain.Camp;
import domain.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CannonTest {

    @Test
    void 다른_기물을_넘고_이동_가능() {
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);
        Horse piece = new Horse(Camp.HAN);

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
        Board board = Board.empty();
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
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);
        Horse pieceA = new Horse(Camp.HAN);
        Horse pieceB = new Horse(Camp.HAN);

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
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);
        Horse pieceA = new Horse(Camp.CHO);
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
    void 궁성_내에서_대각성_이동_시_궁성을_벗어날_수_없다() {
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);

        Position fromPosition = new Position(3, 0);

        board.locatePiece(fromPosition, cannon);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(6, 3), board));
    }

    @Test
    void 궁성_내에서_기물을_넘고_대각선_이동() {
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);
        Horse piece = new Horse(Camp.HAN);

        Position fromPosition = new Position(3, 0);
        Position anotherPiecePosition = new Position(4, 1);

        board.locatePiece(fromPosition, cannon);
        board.locatePiece(anotherPiecePosition, piece);

        Assertions.assertTrue(cannon.canMove(fromPosition, new Position(5, 2), board));
    }

    @Test
    void 궁성_내에서_대각선_이동_불가() {
        Board board = Board.empty();
        Cannon cannon = new Cannon(Camp.HAN);

        Position fromPosition = new Position(3, 0);

        board.locatePiece(fromPosition, cannon);

        Assertions.assertFalse(cannon.canMove(fromPosition, new Position(5, 2), board));
    }
}
