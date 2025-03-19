package janggi.board;

import janggi.Camp;
import janggi.Point;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Soldier;

public class BoardGenerator {

    public static Board generate() {
        Board board = new Board();
        placeChuPieces(board);
        placeHanPieces(board);
        return board;
    }

    private static void placeHanPieces(Board board) {
        board.placePiece(new Point(1, 7), new Soldier(Camp.HAN));
        board.placePiece(new Point(3, 7), new Soldier(Camp.HAN));
        board.placePiece(new Point(5, 7), new Soldier(Camp.HAN));
        board.placePiece(new Point(7, 7), new Soldier(Camp.HAN));
        board.placePiece(new Point(9, 7), new Soldier(Camp.HAN));

        board.placePiece(new Point(2, 8), new Cannon(Camp.HAN));
        board.placePiece(new Point(8, 8), new Cannon(Camp.HAN));

        board.placePiece(new Point(5, 9), new General(Camp.HAN));

        board.placePiece(new Point(1, 10), new Chariot(Camp.HAN));
        board.placePiece(new Point(2, 10), new Elephant(Camp.HAN));
        board.placePiece(new Point(3, 10), new Horse(Camp.HAN));
        board.placePiece(new Point(4, 10), new Guard(Camp.HAN));
        board.placePiece(new Point(6, 10), new Guard(Camp.HAN));
        board.placePiece(new Point(7, 10), new Elephant(Camp.HAN));
        board.placePiece(new Point(8, 10), new Horse(Camp.HAN));
        board.placePiece(new Point(9, 10), new Chariot(Camp.HAN));
    }

    private static void placeChuPieces(Board board) {
        board.placePiece(new Point(1, 1), new Chariot(Camp.CHU));
        board.placePiece(new Point(2, 1), new Elephant(Camp.CHU));
        board.placePiece(new Point(3, 1), new Horse(Camp.CHU));
        board.placePiece(new Point(4, 1), new Guard(Camp.CHU));
        board.placePiece(new Point(6, 1), new Guard(Camp.CHU));
        board.placePiece(new Point(7, 1), new Elephant(Camp.CHU));
        board.placePiece(new Point(8, 1), new Horse(Camp.CHU));
        board.placePiece(new Point(9, 1), new Chariot(Camp.CHU));

        board.placePiece(new Point(5, 2), new General(Camp.CHU));

        board.placePiece(new Point(2, 3), new Cannon(Camp.CHU));
        board.placePiece(new Point(8, 3), new Cannon(Camp.CHU));

        board.placePiece(new Point(1, 4), new Soldier(Camp.CHU));
        board.placePiece(new Point(3, 4), new Soldier(Camp.CHU));
        board.placePiece(new Point(5, 4), new Soldier(Camp.CHU));
        board.placePiece(new Point(7, 4), new Soldier(Camp.CHU));
        board.placePiece(new Point(9, 4), new Soldier(Camp.CHU));
    }
}
