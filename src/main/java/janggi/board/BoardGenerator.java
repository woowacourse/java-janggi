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
        board.placePiece(new Point(0, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Point(2, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Point(4, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Point(6, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Point(8, 6), new Soldier(Camp.HAN, board));

        board.placePiece(new Point(1, 7), new Cannon(Camp.HAN, board));
        board.placePiece(new Point(7, 7), new Cannon(Camp.HAN, board));

        board.placePiece(new Point(4, 8), new General(Camp.HAN, board));

        board.placePiece(new Point(0, 9), new Chariot(Camp.HAN, board));
        board.placePiece(new Point(1, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Point(2, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Point(3, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Point(5, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Point(6, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Point(7, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Point(8, 9), new Chariot(Camp.HAN, board));
    }

    private static void placeChuPieces(Board board) {
        board.placePiece(new Point(0, 0), new Chariot(Camp.CHU, board));
        board.placePiece(new Point(1, 0), new Elephant(Camp.CHU, board));
        board.placePiece(new Point(2, 0), new Horse(Camp.CHU, board));
        board.placePiece(new Point(3, 0), new Guard(Camp.CHU, board));
        board.placePiece(new Point(5, 0), new Guard(Camp.CHU, board));
        board.placePiece(new Point(6, 0), new Elephant(Camp.CHU, board));
        board.placePiece(new Point(7, 0), new Horse(Camp.CHU, board));
        board.placePiece(new Point(8, 0), new Chariot(Camp.CHU, board));

        board.placePiece(new Point(4, 1), new General(Camp.CHU, board));

        board.placePiece(new Point(1, 2), new Cannon(Camp.CHU, board));
        board.placePiece(new Point(7, 2), new Cannon(Camp.CHU, board));

        board.placePiece(new Point(0, 3), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(2, 3), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(4, 3), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(6, 3), new Soldier(Camp.CHU, board));
        board.placePiece(new Point(8, 3), new Soldier(Camp.CHU, board));
    }
}
