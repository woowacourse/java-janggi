package janggi.board;

import janggi.piece.Camp;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Soldier;
import janggi.position.Position;
import java.util.List;

public class BoardGenerator {

    public static Board generate() {
        Board board = new Board();
        placeChoPieces(board);
        placeHanPieces(board);
        return board;
    }

    private static void placeChoPieces(Board board) {
        board.placePiece(new Position(0, 0), new Chariot(Camp.CHO, board));
        board.placePiece(new Position(1, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(2, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(3, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(4, 1), new General(Camp.CHO, board));
        board.placePiece(new Position(5, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(6, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(7, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(8, 0), new Chariot(Camp.CHO, board));

        for (int x : List.of(1, 7)) {
            board.placePiece(new Position(x, 2), new Cannon(Camp.CHO, board));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(new Position(x, 2), new Soldier(Camp.CHO, board));
        }
    }

    private static void placeHanPieces(Board board) {
        board.placePiece(new Position(0, 9), new Chariot(Camp.HAN, board));
        board.placePiece(new Position(1, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(2, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(3, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(4, 8), new General(Camp.HAN, board));
        board.placePiece(new Position(5, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(6, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(7, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(8, 9), new Chariot(Camp.HAN, board));

        for (int x : List.of(1, 7)) {
            board.placePiece(new Position(x, 7), new Cannon(Camp.HAN, board));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(new Position(x, 6), new Soldier(Camp.HAN, board));
        }
    }

}
