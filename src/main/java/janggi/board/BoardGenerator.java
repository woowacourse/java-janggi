package janggi.board;

import janggi.piece.Camp;
import janggi.position.Position;
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
        board.placePiece(new Position(0, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Position(2, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Position(4, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Position(6, 6), new Soldier(Camp.HAN, board));
        board.placePiece(new Position(8, 6), new Soldier(Camp.HAN, board));

        board.placePiece(new Position(1, 7), new Cannon(Camp.HAN, board));
        board.placePiece(new Position(7, 7), new Cannon(Camp.HAN, board));

        board.placePiece(new Position(4, 8), new General(Camp.HAN, board));

        board.placePiece(new Position(0, 9), new Chariot(Camp.HAN, board));
        board.placePiece(new Position(1, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(2, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(3, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(5, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(6, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(7, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(8, 9), new Chariot(Camp.HAN, board));
    }

    private static void placeChuPieces(Board board) {
        board.placePiece(new Position(0, 0), new Chariot(Camp.CHO, board));
        board.placePiece(new Position(1, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(2, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(3, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(5, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(6, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(7, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(8, 0), new Chariot(Camp.CHO, board));

        board.placePiece(new Position(4, 1), new General(Camp.CHO, board));

        board.placePiece(new Position(1, 2), new Cannon(Camp.CHO, board));
        board.placePiece(new Position(7, 2), new Cannon(Camp.CHO, board));

        board.placePiece(new Position(0, 3), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(2, 3), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(4, 3), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(6, 3), new Soldier(Camp.CHO, board));
        board.placePiece(new Position(8, 3), new Soldier(Camp.CHO, board));
    }
}
